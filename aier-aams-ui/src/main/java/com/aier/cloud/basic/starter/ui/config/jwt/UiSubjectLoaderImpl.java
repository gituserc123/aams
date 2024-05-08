package com.aier.cloud.basic.starter.ui.config.jwt;

import com.aier.cloud.basic.common.properties.AierUiProperties;
import com.aier.cloud.basic.common.util.SpringUtils;
import com.aier.cloud.basic.web.shiro.ShiroUser;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import com.aier.cloud.center.common.context.AierUser;
import com.aier.cloud.center.common.context.SubjectLoader;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Random;

/**
 * ui层获取登录用户的实现
 *
 * @author xiaokek
 * @since 2018年2月5日 上午9:40:11
 */
public class UiSubjectLoaderImpl implements SubjectLoader{

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
		Random random = new Random(); // 创建Random对象
		Map<String,String> userMap = Maps.newHashMap();
		userMap.put("userName","测试姓名11");
		userMap.put("userAge",String.valueOf(random.nextInt(100)));
		au.setMasterSlaveCookie(userMap);
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
