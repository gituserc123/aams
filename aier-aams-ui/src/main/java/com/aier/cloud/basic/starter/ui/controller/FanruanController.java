package com.aier.cloud.basic.starter.ui.controller;

import com.aier.cloud.basic.common.properties.AierUiProperties;
import com.aier.cloud.basic.common.properties.FanRuanProperties;
import com.aier.cloud.basic.starter.ui.feign.PlatformConfigService;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

@Controller
public class FanruanController extends BaseController{

	/** 默认忽略参数 */
    private static final String[] DEFAULT_IGNORE_PARAMETERS = new String[] {"url","password"};

    /** 动态参数获取 */
    private static final String SSO_PARAM_STAFFID = "staff";

    private static final String SSO_PARAM_STAFF_NAME = "sname";
    
    private static final String SSO_PARAM_TENANT_ID = "tenant";

    private static final String SSO_PARAM_HOSP_NAME = "hname";
    
    private static final String SSO_PARAM_PLATFORM_NAME = "p";
    
    private static final String SSO_PARAM_FR_VERSION = "v";

    private static final String SSO_PARAM_ISCOMMON = "common";
    
    private static final String SSO_PARAM_COMMON = "0";

    private static final String SSO = "sys/sso";
   
    /** 斜线http请求符 */
    private static final String SEPARATOR_HTTP_COMMA = "/";
    
    /** 斜线http请求参数符 */
    private static final String SEPARATOR_HTTP_COMMA_PARAM = "&";
    
    /** 斜线http请求参数 等号符 */
    private static final String SEPARATOR_HTTP_COMMA_PARAM_EQUAL = "=";
    
    private static final String FR_AHIS_REPORT_URL_FR_8 = "/WebReport/ReportServer"; //reportlet=ahis/

    private static final String FR_AHIS_REPORT_URL_FR_10 = "/webroot/decision/view/report"; //reportlet=ahis/
    
    private static final String REPORT_CPT = "_report.cpt";
    
    private static final String REPORT_FRM = ".frm";

    @Autowired
    private AierUiProperties aierUiProperties;
    
    @Autowired
    private PlatformConfigService platformConfigService;

    /**
     * 请求帆软报表页面，走jsonp跨域请求协议
     * @param request
     * @param url
     * @param map
     * @return
     */
    @RequestMapping(value="/fr", method = RequestMethod.GET)
    public String frReport(HttpServletRequest request, String url, Map<String, Object> map) {
        boolean isCommon = false;
        final StringBuffer parameter = new StringBuffer();
        final Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap != null) {
            for (Entry<String, String[]> entry : parameterMap.entrySet()) {
                String parameterName = entry.getKey();
                if (!ArrayUtils.contains(DEFAULT_IGNORE_PARAMETERS, parameterName)) {
                    String[] parameterValues = entry.getValue();
                    if (parameterValues != null) {
                        for (String parameterValue : parameterValues) {
                            if(parameterName.equalsIgnoreCase(SSO_PARAM_ISCOMMON)) {
                                isCommon = true;
                            }
                            else{
                                parameter.append(SEPARATOR_HTTP_COMMA_PARAM + parameterName + SEPARATOR_HTTP_COMMA_PARAM_EQUAL + parameterValue);
                            }
                        }
                    }
                }
            }
        }
        
        // 默认带租户和机构名称
        String tenantId  = request.getParameter(SSO_PARAM_TENANT_ID);
        String hname     = request.getParameter(SSO_PARAM_HOSP_NAME);
        String platform  = request.getParameter(SSO_PARAM_PLATFORM_NAME);

        parameter.append(SEPARATOR_HTTP_COMMA_PARAM).append(SSO_PARAM_TENANT_ID).append(SEPARATOR_HTTP_COMMA_PARAM_EQUAL).append(Optional.ofNullable(tenantId).orElse(ShiroUtils.getTenantId().toString()));
        parameter.append(SEPARATOR_HTTP_COMMA_PARAM).append(SSO_PARAM_HOSP_NAME).append(SEPARATOR_HTTP_COMMA_PARAM_EQUAL).append(Optional.ofNullable(hname).orElse(ShiroUtils.getInstName()));
        parameter.append(SEPARATOR_HTTP_COMMA_PARAM).append(SSO_PARAM_STAFFID).append(SEPARATOR_HTTP_COMMA_PARAM_EQUAL).append(ShiroUtils.getId());
        parameter.append(SEPARATOR_HTTP_COMMA_PARAM).append(SSO_PARAM_STAFF_NAME).append(SEPARATOR_HTTP_COMMA_PARAM_EQUAL).append(ShiroUtils.getLoginName());
        
        /** 确认帆软版本 默认为8*/
        String frVersion = "8";
        
        String frUri = aierUiProperties.getFrUri();
        map.put("fruserReport", aierUiProperties.getFrUser());
        map.put("frpwdReport", aierUiProperties.getFrPwd());

        if (CollectionUtils.isNotEmpty(aierUiProperties.getFrConfig())) {
            //临时兼容测试环境和正式环境
            try {
            	frVersion = StringUtils.isNotBlank(request.getParameter(SSO_PARAM_FR_VERSION)) ? request.getParameter(SSO_PARAM_FR_VERSION) : platformConfigService.getFrVersion();
            } catch (Exception e) {
            	frVersion = StringUtils.isNotBlank(request.getParameter(SSO_PARAM_FR_VERSION)) ? request.getParameter(SSO_PARAM_FR_VERSION) : "8";
    		}
            for (FanRuanProperties fr : aierUiProperties.getFrConfig()) {
                if (fr.getVersion().equals(frVersion) && "report".equals(fr.getType()) 
                		&& (url.contains(REPORT_FRM) || url.contains(REPORT_CPT))) { // 报表查询
                    frUri = fr.getFrUri();
                    map.put("fruserReport", fr.getFrUser());
                    map.put("frpwdReport", fr.getFrPwd());
                    break;
                } else if (fr.getVersion().equals(frVersion) && "print".equals(fr.getType()) && !url.contains(REPORT_CPT)) { // 打印
                    frUri = fr.getFrUri();
                    map.put("fruserReport", fr.getFrUser());
                    map.put("frpwdReport", fr.getFrPwd());
                    break;
                }
            }
        }
        
        /** 拼装带容器的地址*/
        final StringBuffer requestUrl = new StringBuffer();
        requestUrl.append(frUri)
                .append((frVersion.equals("10")) ? FR_AHIS_REPORT_URL_FR_10 : FR_AHIS_REPORT_URL_FR_8);
        map.put("frUrlCont", requestUrl.toString());
        /** 带上参数*/
        requestUrl.append((frVersion.equals("10")) ? "?viewlet=" : (url.contains(REPORT_FRM) ? "?formlet=" : "?reportlet="))
                .append(StringUtils.isNotBlank(platform) ? platform : aierUiProperties.getSiteCode()).append(SEPARATOR_HTTP_COMMA)
                .append(isCommon ? SSO_PARAM_COMMON : Optional.ofNullable(tenantId).orElse(ShiroUtils.getTenantId().toString()))
                .append(SEPARATOR_HTTP_COMMA)
                .append(url);
        
        map.put("url", 		 requestUrl.toString() + parameter);
        logger.info("url={}",url+parameter);
        return SSO;
    }
	
}
