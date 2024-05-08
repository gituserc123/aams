package com.aier.cloud.basic.starter.ui.shiro;

import com.aier.cloud.basic.common.properties.AierUiProperties;
import com.aier.cloud.basic.web.shiro.RedisSessionDAO;
import com.aier.cloud.basic.web.shiro.filter.BasicUserFilter;
import com.aier.cloud.basic.web.shiro.filter.InstitutionFormAuthenticationFilter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author rain_deng
 * @since 2018年1月29日 下午3:57:37
 */
@Configuration
@ConditionalOnProperty(prefix = "aier.ui", name="default-shiro", havingValue = "true", matchIfMissing = true)
public class ShiroConfig {
	 
    private Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
    
    @Bean
    public RedisSessionDAO getRedisSessionDAO(RedisManager redisManager, AierUiProperties properties) {
        RedisSessionDAO dao = new RedisSessionDAO();
        dao.setSessionInMemoryEnabled(false);
        dao.setRedisManager(redisManager);
        dao.setExpire(properties.getSessionInvalidateTime());
        return dao;
    }
    
    @Bean
    public DefaultWebSessionManager getDefaultWebSessionManager(CacheManager cacheShiroManager, RedisManager redisManager, AierUiProperties properties) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(getRedisSessionDAO(redisManager,properties));
        sessionManager.setGlobalSessionTimeout(properties.getSessionInvalidateTime() * 1000);
      
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setName("shiroCookie");
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }
    
    /**
     * Shiro的过滤器链
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, AierUiProperties properties) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        
        /** 统一的登陆访问url */
        shiroFilter.setLoginUrl("/login");
        
        /** 登陆成功后跳转的url */
        shiroFilter.setSuccessUrl("/home");
        
        /** 没有权限跳转的url */
        shiroFilter.setUnauthorizedUrl("/global/error");
        
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        /*LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login");
        filters.put("logout", logoutFilter);*/
        filters.put("user", new BasicUserFilter());
        filters.put("authc", institutionFormAuthenticationFilter());
        shiroFilter.setFilters(filters);
        
        /**
         * 配置shiro拦截器链
         * anon  不需要认证
         * authc 需要认证
         * user  验证通过或RememberMe登录的都可以
         * 当应用开启了rememberMe时,用户下次访问时可以是一个user,但不会是authc,因为authc是需要重新认证的
         * 顺序从上到下,优先级依次降低
         */
        Map<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("/static/**",           "anon");
        hashMap.put("/global/sessionError", "anon");
        hashMap.put("/kaptcha",             "anon");
        hashMap.put("/login/getInst",       "anon");
        hashMap.put("/login/getDept",       "anon");
        hashMap.put("/updatePwd",           "anon");
        hashMap.put("/login",               "authc");
        hashMap.put("/logout",              "anon");
        
        //feign外部接口调用
        hashMap.put("/api/outinterface/**",              "anon");
        hashMap.put("/**",                  "user");
        
        shiroFilter.setFilterChainDefinitionMap(hashMap);
		
        return shiroFilter;
    }
    
    @Bean
    public ShiroDbRealm getShiroDbRealm(){
        return new ShiroDbRealm() {
        	{
        		setAuthorizationCacheName("authorizationCache");
        	}
        };
    }
    
    @Bean
    public SecurityManager securityManager(@Qualifier("shiroRedisCacheManager")CacheManager cacheManager, 
                                           @Qualifier("RedisManager") RedisManager redisManager,
                                           AierUiProperties properties) {
        //配置核心安全事务管理器
        logger.info("--------------shiro已经加载111----------------");
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        
        //注入缓存管理器;
        manager.setCacheManager(cacheManager);
        
        manager.setSessionManager(getDefaultWebSessionManager(cacheManager,redisManager,properties));
        
        manager.setRealm(getShiroDbRealm());
        
        //注入记住我管理器;
        manager.setRememberMeManager(rememberMeManager());
        
        SecurityUtils.setSecurityManager(manager);
        return manager;
    }
    
    /**
     * rememberMe管理器, cipherKey生成见{@code Base64Test.java}
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decode("Z3VucwAAAAAAAAAAAAAAAA=="));
        manager.setCookie(rememberMeCookie());
        return manager;
    }

    /**
     * 记住密码Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(7 * 24 * 60 * 60);
        return simpleCookie;
    }
    
    /**
     * 验证码表单验证器
     */
    @Bean
    public InstitutionFormAuthenticationFilter institutionFormAuthenticationFilter() {
        return new InstitutionFormAuthenticationFilter();
    }
}
