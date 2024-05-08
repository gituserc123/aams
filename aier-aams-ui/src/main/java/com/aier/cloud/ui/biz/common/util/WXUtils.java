package com.aier.cloud.ui.biz.common.util;

import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;

import com.aier.cloud.basic.api.response.domain.sys.Institution;
import com.aier.cloud.basic.api.response.domain.sys.Staff;
import com.aier.cloud.basic.starter.ui.feign.InstitutionService;
import com.aier.cloud.basic.starter.ui.feign.StaffService;
import com.aier.cloud.basic.web.shiro.InstitutionUserCodePasswordToken;
import com.aier.cloud.basic.web.shiro.exception.IncorrectInstException;

public class WXUtils {
	
	@Resource
	private StaffService staffService;
	
	@Resource
    private InstitutionService instService;
	
	public void validationLogin(AuthenticationToken authcToken) {
		InstitutionUserCodePasswordToken token = (InstitutionUserCodePasswordToken) authcToken; 
		String  username         = token.getUsername();
		Long    institution      = token.getInstitution();
		Long    dept             = token.getDept();
		String  ip               = token.getHost();
		String  loginToken       = token.getLoginToken();
		
		if (StringUtils.isNotBlank(username)) {
			Staff staff = staffService.getByName(username);
            if (staff == null) {
                throw new IncorrectCredentialsException("账号信息不存在");
            } else {
            	if (!staff.getStatus()) {
                    throw new DisabledAccountException("对不起，账号已经暂停使用");
                }
        	    
            	Institution loginInst = instService.getById(institution);
                if (Objects.isNull(loginInst)) {
                    throw new IncorrectInstException("未授权或不存在的机构");
                }
                
                Institution loginDept = instService.getById(dept);
                if (Objects.isNull(loginDept)) {
                    throw new IncorrectInstException("未授权或不存在的科室/部门");
                }
            }
		} else {
		    throw new UnknownAccountException("账号为空");
		}
		
	}

}
