/**
 * 
 */
package com.aier.cloud.basic.starter.ui.controller;

import com.aier.cloud.basic.common.properties.AierUiProperties;
import com.aier.cloud.basic.log.annotion.AierLog;
import com.aier.cloud.basic.log.bean.LogMessage;
import com.aier.cloud.basic.log.utils.LogUtils;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author rain_deng
 *
 */
@Controller
public class LogoutController extends BaseController{
    
    @Autowired
    private AierUiProperties aierUiProperties;
     
    @AierLog(message="{0}注销了系统", module="系统注销")
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        Optional<String> loginName =  Optional.ofNullable(ShiroUtils.getLoginName()); 
        if (loginName.isPresent()) {
            LogUtils.putArgs(LogMessage.newWrite().setParams(ShiroUtils.getLoginName()));
            SecurityUtils.getSubject().logout();
        }
        String http = request.getHeader("X-Forwarded-Proto");
        if(StringUtils.isNotBlank(http)){
        	return redirectTo(Optional.ofNullable(http+":"+aierUiProperties.getPortalUri()).orElse(StringUtils.EMPTY) + "/login");
        }else{
        	return redirectTo(Optional.ofNullable(aierUiProperties.getPortalUri()).orElse(StringUtils.EMPTY) + "/login");
        }
    }
    
}
