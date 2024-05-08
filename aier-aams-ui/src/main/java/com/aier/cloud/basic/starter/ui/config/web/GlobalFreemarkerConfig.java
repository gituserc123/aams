package com.aier.cloud.basic.starter.ui.config.web;

import com.aier.cloud.basic.common.properties.AierUiProperties;
import com.aier.cloud.basic.common.util.SpringUtils;
import com.aier.cloud.basic.web.template.directive.BaseDirective;
import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * <b>类名称：</b>GlobalFreemarkerConfig<br/>
 * <b>类描述：</b>Freemarker 配置<br/>
 * <b>创建人：</b>rain_deng<br/>
 * @version 1.0.0<br/>
 *@author rain_deng
 */
@Configuration
public class GlobalFreemarkerConfig  {
    
    @Autowired
    protected FreeMarkerProperties properties;
    
    @Autowired
    private AierUiProperties aierUiProperties;

	@Value("${spring.profiles.active}")
    private String profile;
    
    @Bean("freeMarkerConfigurer")
    @ConditionalOnMissingBean({FreeMarkerConfig.class})
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding("UTF-8");
        configurer.setTemplateLoaderPaths("/WEB-INF/views", "/WEB-INF/templates", "classpath:/");
        applyProperties(configurer);
        configurer.afterPropertiesSet();
        configurer.getConfiguration().setSharedVariable("shiro", new ShiroTags());
        return configurer;
    }
    
    protected void applyProperties(FreeMarkerConfigurationFactory factory) {
        factory.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
        factory.setDefaultEncoding(this.properties.getCharsetName());
        Properties settings = new Properties();
        settings.put("default_encoding", "UTF-8");
        settings.put("output_encoding", "UTF-8");
        settings.put("url_escaping_charset", "UTF-8");
        settings.put("locale", "zh_CN");
        settings.put("template_update_delay", "0");
        settings.put("whitespace_stripping", "true");
        settings.put("classic_compatible", "true");
        settings.put("tag_syntax", "auto_detect");
        settings.put("number_format", "0.######");
        settings.put("boolean_format", "true,false");
        settings.put("datetime_format", "yyyy-MM-dd");
        settings.put("date_format", "yyyy-MM-dd");
        settings.put("time_format", "HH:mm:ss");
        settings.put("object_wrapper", "freemarker.ext.beans.BeansWrapper");
        settings.putAll(this.properties.getSettings());
        factory.setFreemarkerSettings(settings);
        
        Map<String, Object> variables = new HashMap<String, Object>(2);
        variables.put("xml_escape", "fmXmlEscape");
        if (aierUiProperties.getOpenStaticResources()) {
        	variables.put("env", profile);
        }
        applyDirective(variables);
        factory.setFreemarkerVariables(variables);
        
    }


    /**
     * 初始化指令
     * @param variables
     */
    private void applyDirective(Map<String, Object> variables) {
    	String[] names = SpringUtils.getApplicationContext().getBeanNamesForType(BaseDirective.class);
    	for(String name : names) {
    		variables.put(name, SpringUtils.getBean(name));
    	}
	}

	@Bean
    @ConditionalOnProperty(name = "spring.freemarker.enabled", matchIfMissing = true)
    public FreeMarkerViewResolver getFreemarkViewResolver() {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setCache(false);
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setContentType("text/html; charset=UTF-8");
        freeMarkerViewResolver.setExposeRequestAttributes(true);
        freeMarkerViewResolver.setAllowRequestOverride(true);
        freeMarkerViewResolver.setAllowSessionOverride(true);
        freeMarkerViewResolver.setExposeSessionAttributes(true);
        freeMarkerViewResolver.setExposeSpringMacroHelpers(false);
        freeMarkerViewResolver.setOrder(0);
        freeMarkerViewResolver.setViewClass(GlobalFreemarkerView.class);
        return freeMarkerViewResolver;
    }
    
}
