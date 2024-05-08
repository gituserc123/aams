package com.aier.cloud.basic.starter.ui.controller;

import com.aier.cloud.basic.api.response.domain.sys.Institution;
import com.aier.cloud.basic.common.util.ExceptionUtils;
import com.aier.cloud.basic.log.annotion.AierLog;
import com.aier.cloud.basic.log.bean.LogMessage;
import com.aier.cloud.basic.log.utils.LogUtils;
import com.aier.cloud.basic.starter.ui.feign.InstitutionService;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import com.aier.cloud.basic.web.shiro.exception.IncorrectCaptchaException;
import com.aier.cloud.basic.web.shiro.exception.IncorrectInstException;
import com.aier.cloud.basic.web.shiro.exception.LoginTimeOutException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * <b>类名称：</b>LoginController<br/>
 * <b>类描述：</b>用户登陆<br/>
 * <b>创建人：</b>rain_deng<br/>
 * <b>修改人：</b>rain_deng<br/>
 * <b>修改时间：</b>2017年11月15日 下午2:23:07<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * @author rain_deng
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{
	
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class); 
	
	private static final String LOGIN_PAGE = "sys/login";
	
	@Value("${spring.profiles.active}")
    private String profile;
	
	@Autowired
	private InstitutionService institutionService;

	@GetMapping
	public String login(HttpServletRequest request) {
	    Subject s = SecurityUtils.getSubject();
	    request.setAttribute("profile", profile);
        return s.isRemembered() || s.isAuthenticated() ? redirectTo("home") : LOGIN_PAGE;
	}
	
    @RequestMapping(method = {RequestMethod.GET}, params="ajax=true")
    public @ResponseBody
    Map<String,Object> loginDialog2AJAX(HttpServletResponse response) {
        response.setStatus(500);
        return loginDialog();
    }
    
    @RequestMapping(method = {RequestMethod.GET}, headers = "X-Requested-With=XMLHttpRequest")
    public @ResponseBody
    Map<String,Object> loginDialog() {
        return ajaxTimeout("会话超时，请重新登录。");
    }

    @AierLog(message="{0}登录了系统", module="系统登录（异步）")
    @RequestMapping(value = "/timeout/success", method = {RequestMethod.GET})
    public @ResponseBody Map<String,Object> timeoutSuccess() {
        LogUtils.putArgs(LogMessage.newWrite().setParams(ShiroUtils.getLoginName()));
        return success("登录成功。");
    }
	
	@RequestMapping(method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username, 
                       Map<String, Object> map, HttpServletRequest request) {
        String msg = parseException(request);
        map.put("msg", msg);
        return LOGIN_PAGE;
    }
	
	@RequestMapping(method = {RequestMethod.POST}, headers = "x-requested-with=XMLHttpRequest")
    public @ResponseBody
    Map<String,Object> failDialog(HttpServletRequest request) {
        String msg = parseException(request);
        return fail(msg);
    }
	
	private String parseException(HttpServletRequest request) {
        String errorString = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        Class<?> error = null;
        try {
            if (errorString != null) {
                error = Class.forName(errorString);
            }
        } catch (ClassNotFoundException e) {
            LOG.error(ExceptionUtils.getStackTraceAsString(e));
        } 
        
        String msg = "其他错误！";
        if (error != null) {
        	if (error.equals(IncorrectCaptchaException.class)) {
        		msg = "该账号无角色权限，无法登录！";
        	}
        	else if (error.equals(LoginTimeOutException.class)){
                msg = "登录页停留时间过长，请再次登录！";
            }
        	else if (error.equals(IncorrectCredentialsException.class)){
                msg = "账户或密码错误！";
            }
            else if (error.equals(UnknownAccountException.class)){
                msg = "未知帐号错误！";
            }
            else if (error.equals(IncorrectInstException.class)){
                msg = "未授权或不存在的机构！";
            }
            else if (error.equals(AuthenticationException.class)){
                msg = "认证失败！";
            }
            else if (error.equals(DisabledAccountException.class)){
                msg = "账号已经被冻结，无法使用！";
            }
            else if (error.equals(LockedAccountException.class)){
                msg = "密码错误输入次数超过5次，账号已被锁定！2分钟后自动解锁，请稍后再试";
            }
            else {
                msg = "未知帐号错误！";
            }
        }
        return "登录失败，" + msg;
    }
	
	
	/**
	 * 1. 获取登录用户名对应的医院名称和医院租户id
	 * 2. 如果用户是在医院某个科室，则需要获取到医院租户id
	 * 3. 如果用户没挂靠在科室，则直接获取医院租户id
	 */
    @PostMapping(value = "/getInst")
	public @ResponseBody Map<String, Object> getInstitution(String loginCode) {
    	/**
    	 * 1. 获取登录用户名对应的医院名称和医院租户id
    	 * 2. 如果用户是在医院某个科室，则需要获取到医院租户id
    	 * 3. 如果用户没挂靠在科室，则直接获取医院租户id
    	 */
	    List<Institution> data = institutionService.getListByStaffCode(loginCode);
	    return success(data);
	}
	
    @PostMapping(value = "/getDept")
    public @ResponseBody Map<String, Object> getDept(String loginCode, Long institution) {
        List<Institution> data = institutionService.getDeptListByStaffCodeAndInst(loginCode, institution);
        return success(data);
    }
}
