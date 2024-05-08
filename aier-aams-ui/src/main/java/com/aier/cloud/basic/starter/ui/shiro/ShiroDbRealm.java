package com.aier.cloud.basic.starter.ui.shiro;

import com.aier.cloud.aams.api.request.domain.SysModule;
import com.aier.cloud.aams.api.request.domain.SysPermission;
import com.aier.cloud.basic.api.domain.enums.InstEnum;
import com.aier.cloud.basic.api.response.domain.sys.Institution;
import com.aier.cloud.basic.api.response.domain.sys.Permission;
import com.aier.cloud.basic.api.response.domain.sys.Staff;
import com.aier.cloud.basic.common.properties.AierUiProperties;
import com.aier.cloud.basic.common.util.DateUtils;
import com.aier.cloud.basic.common.util.DigestUtils;
import com.aier.cloud.basic.common.util.EncodeUtils;
import com.aier.cloud.basic.starter.ui.feign.InstitutionService;
import com.aier.cloud.basic.starter.ui.feign.StaffService;
import com.aier.cloud.basic.web.shiro.BaseShiroDbRealm;
import com.aier.cloud.basic.web.shiro.InstitutionUserCodePasswordToken;
import com.aier.cloud.basic.web.shiro.ShiroUser;
import com.aier.cloud.basic.web.shiro.exception.IncorrectInstException;
import com.aier.cloud.basic.web.shiro.interfaces.IShiroDbRealmService;
import com.aier.cloud.basic.web.shiro.interfaces.IShiroUser;
import com.aier.cloud.ui.biz.aams.feign.SysModuleFeignService;
import com.aier.cloud.ui.biz.aams.feign.SysPermissionFeignService;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;


/**
 * 
 * 
 * <b>类名称：</b>ShiroDbRealm<br/>
 * <b>类描述：</b>授权认证管理<br/>
 *               处理用户登录，用户权限刷新，授权<br/>
 * <b>创建人：</b>rain_deng<br/>
 * <b>修改人：</b>rain_deng<br/>
 * <b>修改时间：</b>2017年11月15日 下午2:30:40<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *@author rain_deng
 */
//@Component(value = "shiroDbRealm")
public class ShiroDbRealm extends BaseShiroDbRealm implements IShiroDbRealmService{
	
	private final static Logger log = LoggerFactory.getLogger(ShiroDbRealm.class);
	
	/** 用户组织层级分隔符 */
	public static final String USER_ORG_GRADE_SEPARATOR = ",";
	
	/** 最大失败尝试次数 */
	public static final int MAX_TRY_TIMES = 10;
	
	/** 默认密码 */
	public static final String DEFAULT_PASSWORD = "Aier123456";
	
	/**
	 * 登录用户
	 */
	public final static String LOGIN_USER = "login_user"; 
	
	@Resource
    private InstitutionService instService;
	
	@Resource
	private StaffService staffService;
	
	@Resource
    private AierUiProperties aierUiProperties;

	@Autowired
	private SysModuleFeignService sysModuleFeignService;

	@Autowired
	private SysPermissionFeignService sysPermissionFeignService;
	
	/**
	 * 给ShiroDbRealm提供编码信息，用于密码比对
	 */
	public ShiroDbRealm() {
		super();
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ALGORITHM);
		matcher.setHashIterations(INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	
	/**
	 * 认证回调函数, 登录时调用.</br>
	 * 1. 获取登录用户的所属医院/集团 的 机构编码</br>
     * 2. 登录验证，失败，返回信息</br>
     * 3. 验证通过，记录最后登录机构等信息</br>
     * 4. 保存用户会话信息，如租户信息等</br>
	 */
	@Override
	public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
	    /**
         * 1. 获取登录用户的所属医院/集团 的 机构编码
         * 2. 登录验证，失败，返回信息
         * 3. 验证通过，记录最后登录机构等信息
         * 4. 保存用户会话信息，如租户信息等
         */
	    InstitutionUserCodePasswordToken token = (InstitutionUserCodePasswordToken) authcToken; 
		String  username         = token.getUsername();
		String  password         = new String(token.getPassword());
		Long    institution      = token.getInstitution();
		Long    dept             = token.getDept();
		String  ip               = token.getHost();
		
		if (StringUtils.isNotBlank(username)) 
		{
		    Staff staff = staffService.getByName(username);
            if (staff == null) {
                throw new IncorrectCredentialsException("未知账号错误");
            }
            
            if (StringUtils.isNotBlank(password)  && !Objects.isNull(institution) && !Objects.isNull(dept))
            {
                
                return validationLogin(staff, password, institution, dept, ip, staff.getChangePassword() != null ? staff.getChangePassword() : false);
            } 
            // ajax 异步登录
            else if (StringUtils.isNotBlank(password)  && Objects.isNull(institution) && Objects.isNull(dept)) 
            {
                return validationLogin(staff, password, staff.getLastLoginInst(), staff.getLastLoginDept(), ip, staff.getChangePassword() != null ? staff.getChangePassword() : false);
            }
            else 
            {
                throw new UnknownAccountException("未知账户错误");
            }
		}
		else 
		{
		    throw new UnknownAccountException("未知账户错误");
		}
	}

	
	private AuthenticationInfo validationLogin (Staff staff, String password, Long institution, Long dept, String ip, Boolean ischangePassword) {
	    
	    if (!staff.getStatus()) {
            throw new DisabledAccountException("对不起，账号已经暂停使用");
        }
	    
	    /**
	     * 防暴力登录限制，10秒内不能重复登录同一个账号，暂未处理
	     */
        Institution loginInst = instService.getById(institution);
        if (ObjectUtils.isEmpty(loginInst)) {
            throw new IncorrectInstException("未授权或不存在的机构");
        }
        
        Institution loginDept = instService.getById(dept);
        if (ObjectUtils.isEmpty(loginDept)) {
            throw new IncorrectInstException("未授权或不存在的科室/部门");
        }
        
        /** 获取用户关联的所有医院信息 */
        List<Institution> institutions = instService.getListByStaffCode(staff.getCode());
        if (!institutions.contains(loginInst)) {
            throw new IncorrectInstException("未授权或不存在的机构");
        }
        
        /** 获取带dept详细信息的dept集合 */
        List<Institution> depts = instService.getDeptDetailListByStaffCodeAndInst(staff.getCode(), institution);
        if (!(depts.stream().anyMatch(item -> item.getId().equals(loginDept.getId())))) {
            throw new IncorrectInstException("未授权或不存在的科室/部门");
        }
        
        /** 如果用户被锁定，查询自动解锁时间，到时间自动解锁 */
        if (staff.getLocked()) {
            // 2分钟后自动解锁，会在系统配置动态配置
            int loginFailureLockTime = 2; 
            Date lockedDate = staff.getLockedDate() != null ? staff.getLockedDate() : new Date();
            Date unlockDate = org.apache.commons.lang3.time.DateUtils.addMinutes(lockedDate, loginFailureLockTime);
            if (new Date().after(unlockDate)) {
                staff.setLoginFailureCount(0);
                staff.setLocked(false);
                staff.setLockedDate(null);
                staff.setModifer(staff.getId());
                staffService.edit(staff);
            } else {
                throw new LockedAccountException();
            }
        }
        
        /** 如果用户登录密码错误，会记录登录失败次数，超过系统设置的失败登录次数，将会被自动锁定 */
        if (!validatePassword(password, staff.getPassword(), staff.getSalt())) {
            int loginFailureCount = staff.getLoginFailureCount() + 1;
            
            //if (loginFailureCount >= setting.getAccountLockCount() ) {
            if (loginFailureCount >= MAX_TRY_TIMES ) {
                staff.setLocked(true);
                staff.setLockedDate(new Date());
            }
            staff.setLoginFailureCount(loginFailureCount);
            staff.setModifer(staff.getId());
            staffService.edit(staff);
            throw new IncorrectCredentialsException("用户名或密码错误");
        }
        
        staff.setLoginIp(ip);
        staff.setLastLoginTime(new Date());
        staff.setLoginFailureCount(0);
        staff.setModifer(staff.getId());
        staff.setLastLoginInst(institution);
        staff.setLastLoginDept(dept);
        staffService.edit(staff);
        
        /**
         * id 用户id：当前登录的用户id
         * loginName 登录名：当前登录的用户名
         * instId 机构id：当前用户工作执行机构（如，部门，科室等） 暂无
         * instName 机构名称 ：当前用户工作执行机构（如，部门，科室等）暂无
         * tenantId 租户id：当前用户工作执行医院，可为空，空表示是非医院用户,默认非医院是 0
         * isChangePassword 是否更改密码
         * isHosp 是否医院用户
         * isAdmin 是否超级管理员
         * depts 当前关联的科室集合
         * deptId 当前登录的科室id
         */
        AamsUiUser shiroUser = new AamsUiUser(staff.getId(), staff.getCode(), staff.getName(), institution, loginInst.getName(), !Objects.isNull(loginInst.getAhisHosp()) ? Long.valueOf(loginInst.getAhisHosp()) : 0L, ischangePassword, staff.getAdmin(), loginInst.getInstType().equals(InstEnum.HOSP.getInstType()), depts, dept);
        shiroUser.setDeptName(loginDept.getName());
        
        byte[] salt = EncodeUtils.decodeHex(staff.getSalt());
        return new SimpleAuthenticationInfo(shiroUser, staff.getPassword(),ByteSource.Util.bytes(salt), getName());
	}

	/**
	 * 授权查询回调函数, 进行鉴权认证，但缓存中无用户的授权信息时调用.
	 */
	@Override
	public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
	    log.info("##################执行Shiro授权##################");
		Collection<?> collection = principals.fromRealm(getName());
		if (CollectionUtils.isEmpty(collection)) {
			return null;
		}
        ShiroUser shiroUser = (ShiroUser) collection.iterator().next();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 角色  + 站点权限 
		info.addStringPermissions(makePermissions(shiroUser));
		info.addRoles(new ArrayList<>());
		if (log.isInfoEnabled()) {
            log.info("{}拥有的角色:{}", shiroUser.getLoginName(), info.getRoles());
		}
		return info;
	}
	
	/**
	 * 构建权限资源
	 * @param shiroUser
	 * @return
	 */
	private Collection<String> makePermissions(IShiroUser<Long> shiroUser) {
		if (isActiveRoot()) {
			if (shiroUser.getIsAdmin()) {
				List<SysModule> modules = sysModuleFeignService.getModulesByPlatform(aierUiProperties.getSiteCode());
				Collection<String> stringPermissions = Sets.newHashSet();
				for (SysModule module : modules) {
					stringPermissions.add(module.getModuleCode() + Permission.PERMISSION_COLON + Permission.PERMISSION_READ);
					stringPermissions.add(module.getModuleCode() + Permission.PERMISSION_COLON + Permission.PERMISSION_CREATE);
					stringPermissions.add(module.getModuleCode() + Permission.PERMISSION_COLON + Permission.PERMISSION_UPDATE);
					stringPermissions.add(module.getModuleCode() + Permission.PERMISSION_COLON + Permission.PERMISSION_DELETE);
				}

				List<SysPermission> permissions = sysPermissionFeignService.getAllPermissions(aierUiProperties.getSiteCode());
				for (SysPermission permission : permissions) {
					stringPermissions.add(permission.getModuleCode() + Permission.PERMISSION_COLON + permission.getPermCode());
				}

				if (log.isInfoEnabled()) {
					log.info("使用了管理员:{}登录了系统。At {}",shiroUser.getLoginName(), DateUtils.getTime());
					log.info("{}拥有的权限:{}", shiroUser.getLoginName(), stringPermissions);
				}
				return stringPermissions;
			}
		}

		List<SysPermission> permissions = sysPermissionFeignService.selectListByUserAndInst(shiroUser.getId(), shiroUser.getInstId(), aierUiProperties.getSiteCode());

		Collection<String> stringPermissions = Sets.newHashSet();
		for (SysPermission permission : permissions) {
			stringPermissions.add(permission.getModuleCode() + Permission.PERMISSION_COLON + permission.getPermCode());
		}

		if (log.isInfoEnabled()) {
			log.info("{}拥有的权限:{}", shiroUser.getLoginName(), stringPermissions);
		}
		return stringPermissions;
	}

	
	public static class HashPassword {
		public String salt;
		public String password;
		@Override
		public String toString() {
			return "HashPassword [salt=" + salt + ", password=" + password + "]";
		}
	}
	
	/**
	 * 加密密码
	 * @param plainPassword
	 * @return
	 */
	public static HashPassword encryptPassword(String plainPassword) {
		HashPassword result = new HashPassword();
		byte[] salt = DigestUtils.generateSalt(SALT_SIZE);
		result.salt = EncodeUtils.encodeHex(salt);

		byte[] hashPassword = DigestUtils.sha1(plainPassword.getBytes(), salt, INTERATIONS);
		result.password = EncodeUtils.encodeHex(hashPassword);
		return result;
	}
	
	/**
	 * 
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @param salt 盐值
	 * @return
	 */
	public static boolean validatePassword(String plainPassword, String password, String salt) {
		byte[] hashPassword = DigestUtils.sha1(plainPassword.getBytes(), EncodeUtils.decodeHex(salt), INTERATIONS);
		String oldPassword = EncodeUtils.encodeHex(hashPassword);
		return password.equals(oldPassword);
	}
	
	/**
	 * 生成随机密码
	 * @param length 密码位数
	 * @return
	 */
	public static String getRandomPassword(int length) {
	       char[] ss = new char[length];
	       int i=0;
	       while(i<length) {
	           int f = (int) (Math.random()*3);
	           if(f==0)  
	            ss[i] = (char) ('A'+Math.random()*26);
	           else if(f==1)  
	            ss[i] = (char) ('a'+Math.random()*26);
	           else 
	            ss[i] = (char) ('0'+Math.random()*10);    
	           i++;
	        }
	        String str = new String(ss);
	        return str;
	   }
	
}
