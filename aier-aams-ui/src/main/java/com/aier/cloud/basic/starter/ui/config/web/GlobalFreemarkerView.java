package com.aier.cloud.basic.starter.ui.config.web;

import com.aier.cloud.basic.common.properties.AierUiProperties;
import com.aier.cloud.basic.common.properties.FanRuanProperties;
import com.aier.cloud.basic.common.util.SpringUtils;
import com.aier.cloud.basic.starter.ui.feign.PlatformConfigService;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


/**
 * 
 * 
 * <b>类名称：</b>GlobalFreemarkerView<br/>
 * <b>类描述：</b>该类存放freemarker 全局变量，提供页面的全局访问<br/>
 * <b>创建人：</b>rain_deng<br/>
 * <b>修改时间：</b>2018年6月12日 下午1:51:22<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * @author rain_deng
 */
public class GlobalFreemarkerView extends FreeMarkerView {
	
	@Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        AierUiProperties aierUiProperties = SpringUtils.getBean(AierUiProperties.class);
        // 目录方式访问,
        if (Optional.ofNullable(aierUiProperties.getPortalUri()).isPresent() && !aierUiProperties.getHasMutiPort()) {
            model.put("base", "/" + aierUiProperties.getSiteCode());
        } else {
            model.put("base", request.getContextPath());
        }
        model.put("openStaticResources", aierUiProperties.getOpenStaticResources());
        model.put("sysdate", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        model.put("platform", aierUiProperties.getSiteCode());
        model.put("goeasyKey", aierUiProperties.getGoeasyKey());
        model.put("fruri", aierUiProperties.getFrUri());
        model.put("fruser", aierUiProperties.getFrUser());
        model.put("frpwd", aierUiProperties.getFrPwd());
        model.put("tenant", Objects.isNull(ShiroUtils.getTenantId()) ? null : ShiroUtils.getTenantId());
        model.put("hospname", StringUtils.isNotBlank(ShiroUtils.getInstName()) ? ShiroUtils.getInstName() : null);
        model.put("portalUri", Optional.ofNullable(aierUiProperties.getPortalUri()).orElse(StringUtils.EMPTY));
        model.put("weiXinRedirectUri", Optional.ofNullable(aierUiProperties.getWeiXinRedirectUri()).orElse(StringUtils.EMPTY));
        if (!Objects.isNull(aierUiProperties.getQyweixin())) {
        	 model.put("corpid", aierUiProperties.getQyweixin().getCorpid());
        	 model.put("agentId", aierUiProperties.getQyweixin().getAgentId());
        	 model.put("corpsecret", aierUiProperties.getQyweixin().getCorpsecret());
        }
        if (CollectionUtils.isNotEmpty(aierUiProperties.getFrConfig())) {
            /** 单据帆软版本*/
            PlatformConfigService configService = SpringUtils.getBean(PlatformConfigService.class);
            String frVersion = configService.getFrVersion();
            for (FanRuanProperties fr : aierUiProperties.getFrConfig()) {
                if (fr.getVersion().equals(frVersion) && fr.getType().equals("print")) {
                    model.put("fruri", fr.getFrUri());
                    model.put("fruser", fr.getFrUser());
                    model.put("frpwd", fr.getFrPwd());
                    break;
                }
            }
        }
        super.exposeHelpers(model, request);
    }
	
}
