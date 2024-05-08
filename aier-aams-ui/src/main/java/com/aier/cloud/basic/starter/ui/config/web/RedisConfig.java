package com.aier.cloud.basic.starter.ui.config.web;

import com.aier.cloud.basic.cache.redis.FastJson2JsonRedisSerializer;
import com.aier.cloud.basic.common.properties.AierUiProperties;
import com.alibaba.fastjson.parser.ParserConfig;
import org.crazycake.shiro.RedisManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 
 * <b>类名称：</b>RedisConfig<br/>
 * <b>类描述：</b>redis 配置<br/>
 * <b>创建人：</b>rain_deng<br/>
 * <b>修改人：</b>rain_deng<br/>
 * <b>修改时间：</b>2017年11月22日 下午12:03:48<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * @author rain_deng
 */
@Configuration("com.aier.cloud.basic.starter.ui.config.web.RedisConfig")
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
	
    private Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	
	private static final String KEY_SEPARATOR = ".";
	
	@Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.database}")
    private int database;
	
	@Bean  
	@Override
    public KeyGenerator keyGenerator() {  
		return (target, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(KEY_SEPARATOR + method.getName() + KEY_SEPARATOR);
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    } 

	/*@Bean
    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate, AierUiProperties ahisUiProperties) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(ahisUiProperties.getRedisExpirTime());
        return cacheManager;
    }*/
	
	/**
     * 缓存管理器 使用redis实现
     */
	@Bean(name = "shiroRedisCacheManager")
    public org.crazycake.shiro.RedisCacheManager redisCacheManager(AierUiProperties aierUiProperties) {
        logger.info("--------------redis cache init---------------");
        org.crazycake.shiro.RedisCacheManager redisCacheManager = new org.crazycake.shiro.RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setExpire(aierUiProperties.getRedisExpirTime());
        redisCacheManager.setKeyPrefix("shiro:cache:");
        logger.info("--------------redis cache ---------------" + redisCacheManager);
        return redisCacheManager;
    }
    
	@Bean(name = "RedisManager")
    public RedisManager redisManager() {
	    // 这里可以扩展为集群模式 （https://github.com/alexxiyang/shiro-redis）
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPassword(password);
        redisManager.setPort(port);
        redisManager.setDatabase(database);
        return redisManager;
    }
    
    @Bean
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory factory) {
      RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
      redisTemplate.setConnectionFactory(factory);
      //Long类型不可以会出现异常信息;
      RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
      redisTemplate.setKeySerializer(stringRedisSerializer);
      redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer());
      return redisTemplate;
    }
    
    @Bean
    @SuppressWarnings("rawtypes")
    public RedisSerializer fastJson2JsonRedisSerializer() {
    	// 设置fastjson的对象安全类型范围 ，低版本的fastjson会有安全漏洞，已经在高版本中修复
    	ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    	//.addAccept("com.aier."); 
        return new FastJson2JsonRedisSerializer<Object>(Object.class);
    }
	
}
