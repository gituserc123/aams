package com.aier.cloud.basic.starter.ui.config.web;

import com.aier.cloud.basic.web.support.DateEditor;
import com.aier.cloud.basic.web.xss.XssFilter;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Properties;

/**
 * 
 * <b>类名称：</b>WebConfig<br/>
 * <b>类描述：</b>Web 配置<br/>
 * <b>创建人：</b>rain_deng<br/>
 * <b>修改人：</b>rain_deng<br/>
 * <b>修改时间：</b>2017年11月15日 下午2:13:16<br/>
 * <b>修改备注：</b><br/>
 * 
 * @version 1.0.0<br/>
 * @author rain_deng
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("*")
                //跨域允许时间
                .maxAge(7200);
    }

	/**
	 * 注册Controller的参数转换器
	 * 
	 * @param a
	 * @return
	 */
	@Bean
	public RequestMappingHandlerAdapter initBinder(RequestMappingHandlerAdapter a) {
		WebBindingInitializer b = new WebBindingInitializer() {
			@Override
			public void initBinder(WebDataBinder binder) {
				binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
				binder.registerCustomEditor(Date.class, new DateEditor(true));
			}
		};
		a.setWebBindingInitializer(b);
		return a;
	}
	
	@Value("${spring.profiles.active}")
    private String profile;
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		if ("uat".equals(profile)||"prod".equals(profile)) {
//			registry.addInterceptor(new ReferrerInterceptor()).addPathPatterns("/**").excludePathPatterns("/", "/login", "/logout");
		}
    }

	/**
	 * xssFilter注册
	 */
	@Bean
	public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
		XssFilter xssFilter = new XssFilter();
		//万能查询允许传过滤参数,临时解决方案,不安全
		xssFilter.setUrlExclusion(Lists.newArrayList("/ui/sys/autoComplete/query","/ui/common/pinyin4j/getPinYinWord"));

		/** 不包括的路径，一般是内网操作路径 */
		FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<XssFilter>(xssFilter);
		registration.addUrlPatterns("/*");
		return registration;
	}

	/**
	 * RequestContextListener注册
	 */
	@Bean
	public ServletListenerRegistrationBean<RequestContextListener> requestContextListenerRegistration() {
		return new ServletListenerRegistrationBean<>(new RequestContextListener());
	}

	@Bean(name = "validator")
	public LocalValidatorFactoryBean localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

	/**
	 * 验证码
	 * 
	 * @return
	 */
	@Bean
	public DefaultKaptcha getDefaultKaptcha() {
		Properties properties = new Properties();
		properties.setProperty("kaptcha.border", "yes");
		properties.setProperty("kaptcha.border.color", "105,179,90");
		properties.setProperty("kaptcha.textproducer.font.color", "red");
		properties.setProperty("kaptcha.image.width", "110");
		properties.setProperty("kaptcha.image.height", "40");
		properties.setProperty("kaptcha.textproducer.font.size", "35");
		properties.setProperty("kaptcha.session.key", "code");
		properties.setProperty("kaptcha.textproducer.char.length", "4");
		properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
	
	
	/**
	 * 解决UEditor上传图片与spring mvc上传图片冲突问题
	 * @see https://blog.csdn.net/lishuai1028/article/details/51481343
	 * @return
	 */
//	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver c = new CommonsMultipartResolver(){
		    @Override
		    public boolean isMultipart(HttpServletRequest request) {
		        String url = request.getRequestURI();
		        if (url!=null && url.contains("ueditor")) {
		            return false;
		        } else {
		            return super.isMultipart(request);
		        }
		    }
		};
		c.setMaxUploadSize(2*1024*1024L);
		c.setDefaultEncoding("utf-8");
		return c;
	}

}
