package com.aier.cloud.basic.starter.ui.config.jwt;

import com.aier.cloud.basic.common.properties.AierUiProperties;
import com.aier.cloud.basic.common.util.SpringUtils;
import com.aier.cloud.basic.starter.ui.shiro.AamsUiUser;
import com.aier.cloud.basic.web.shiro.ShiroUser;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import com.aier.cloud.center.common.context.AierUser;
import com.aier.cloud.center.common.context.SubjectLoader;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * ui层获取登录用户的实现
 *
 * @author xiaokek
 * @since 2018年2月5日 上午9:40:11
 */
public class UiSubjectLoaderImpl implements SubjectLoader{

	private final static Logger log = LoggerFactory.getLogger(UiSubjectLoaderImpl.class);


	@Override
	public AierUser getUser() {
		ShiroUser su = (ShiroUser)ShiroUtils.getStaff();
		if(su == null) {
			return null;
		}
		AierUser au = new AierUser();
		au.setUserId(su.getId());
		au.setUserName(su.getLoginName());
		au.setTenantId(su.getTenantId());
        au.setInstId(su.getInstId());
        au.setIsHospUser(su.getIsHosp());
        au.setDeptId(su.getDeptId());
        au.setDeptName(su.getDeptName());
        au.setInstName(su.getInstName());
        au.setUserCode(su.getLoginCode());
        au.setAdmin(su.getIsAdmin());
        au.setDateScopeInstIds(su.getDateScopeInstIds());
        AierUiProperties aierUiProperties = SpringUtils.getBean(AierUiProperties.class);
        au.setPlatform(aierUiProperties.getSiteCode());
		// 更新JWT中的UserContext信息
		try{
			AamsUiUser shiroUser = (AamsUiUser)su;
			Map<String,String> userMap = Maps.newHashMap();
			userMap.put("secUser", JSON.toJSONString(shiroUser.getSecUser()));
			userMap.put("auditRoles", JSON.toJSONString(shiroUser.getAuditRoles()));
			userMap.put("deptMasters", JSON.toJSONString(shiroUser.getDeptMasters()));
			/* 调用这段代码就报错，很神奇! 猜测是查询出来的数据问题
			    2025-01-24 经检测，应该是secFunctionalities大小超过了shiro传输的限制，导致序列化失败
			userMap.put("secFunctionalities", JSON.toJSONString(shiroUser.getSecFunctionalities()));
			*/
			Random random = new Random();
			userMap.put("random",String.valueOf(random.nextInt(100)));
			userMap.put("orgMasters", JSON.toJSONString(shiroUser.getOrgMasters()));
			au.setMasterSlaveCookie(userMap);
		}catch (Exception e){
			log.error("更新UserContext失败！" + e.getMessage());
		}
		return au;
	}

	@Override
	public void setUser(AierUser u) {
		//由shiro管理
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove() {
		//由shiro管理
		throw new UnsupportedOperationException();
	}

	@Override
	public Long getUserId() {
		ShiroUser su = (ShiroUser)ShiroUtils.getStaff();
		return su != null ? su.getId() : null;
	}

	@Override
	public String getUserName() {
		ShiroUser su = (ShiroUser)ShiroUtils.getStaff();
		return su != null ? su.getLoginName() : null;
	}

	@Override
	public Long getInstId() {
		ShiroUser su = (ShiroUser)ShiroUtils.getStaff();
		return su != null ? su.getInstId() : null;
	}

	@Override
	public Long getTenantId() {
		ShiroUser su = (ShiroUser)ShiroUtils.getStaff();
		return su != null ? su.getTenantId() : null;
	}

   
    @Override
    public boolean getIsHospUser() {
		ShiroUser su = (ShiroUser)ShiroUtils.getStaff();
		return su != null ? su.getIsHosp() : null;
    }

	@Override
	public Long getDeptId() {
		ShiroUser su = (ShiroUser)ShiroUtils.getStaff();
		return su != null ? su.getDeptId() : null;
	}

	@Override
    public String getDeptName() {
		ShiroUser su = (ShiroUser)ShiroUtils.getStaff();
        return su != null ? su.getDeptName() : null;
    }

	@Override
	public String getInstName() {
		ShiroUser su = (ShiroUser)ShiroUtils.getStaff();
		return su != null ? su.getInstName() : null;
	}

   
    @Override
    public String getUserCode() {
        ShiroUser su = (ShiroUser)ShiroUtils.getStaff();
        return su != null ? su.getLoginCode() : null;
    }

   
    @Override
    public String getPlatform() {
        AierUiProperties aierUiProperties = SpringUtils.getBean(AierUiProperties.class);
        return aierUiProperties.getSiteCode();
    }

}
