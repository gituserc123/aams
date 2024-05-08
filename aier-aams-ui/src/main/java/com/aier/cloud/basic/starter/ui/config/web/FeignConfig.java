package com.aier.cloud.basic.starter.ui.config.web;

import com.aier.cloud.basic.common.exception.ExceptionResolver;
import com.aier.cloud.basic.common.exception.FeignException;
import com.aier.cloud.basic.common.util.JsonUtil;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.slf4j.Slf4jLogger;
import org.apache.commons.collections.MapUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.RetryableFeignLoadBalancer;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.*;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static feign.Util.valuesOrEmpty;

/**
 * FeignConfig
 * 
 * @author xiaokek
 * @since 2018年3月23日 下午4:10:05
 */
@Lazy(value=false)
@Configuration
public class FeignConfig {

	/**
	 * 注册@FeignClient的参数转换器
	 * 
	 * @return
	 */
	@Bean
	public List<FeignFormatterRegistrar> feignFormatterRegistrar() {
		FeignFormatterRegistrar r = new FeignFormatterRegistrar() {
			@Override
			public void registerFormatters(FormatterRegistry registry) {
				registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
			}
		};
		return Lists.newArrayList(r);
	}
	
	

	/**
	 * 注册@FeignClient的参数转换器
	 * 
	 * @return
	 */
	@Bean
	public ErrorDecoder errorDecoder() {
		return new ErrorDecoder() {
			@Override
			public Exception decode(String methodKey, Response response) {
				int code = response.status();
				if(response.body() != null){
					String body = null;
					try{
						body = Util.toString(response.body().asReader());
						Map json = JsonUtil.fromJson(body, Map.class);
						FeignException fe = new FeignException(code, MapUtils.getString(json, "msg"), MapUtils.getString(json, "error"));
						fe.setLevel(MapUtils.getString(json, "level", "error"));
						return fe;
					} catch(Exception e){
						return new RuntimeException(methodKey + ":" + (body == null ? e.getMessage():body));
					}
				}
				return new RuntimeException("feign调用[" + methodKey + "]失败");
			}

		};
	}

	
	
	/*************************************
	 * 以下皆是方便开发环境调试的配置,uat和生产环境不配
	 *************************************/
	
	
	/**
	 * 方便开发时查看日志,上线不需要 所以指定了profile
	 * 
	 * @return
	 */
	@Bean
	@Profile({
			"local"
	})
	public Logger feignLogger(CachingSpringLoadBalancerFactory f) {
		return new Slf4jLogger() {

			/** 存日志*/
			ThreadLocal<List> logs = new ThreadLocal<>();
			/** 存当前请求*/
			ThreadLocal<Request> req = new ThreadLocal<>();
			org.slf4j.Logger logger = LoggerFactory.getLogger("com.aier.feign");
			boolean headers = false;

			@Override
			protected void log(String configKey, String format, Object... args) {
				logger.debug(String.format(ExceptionResolver.getStack("com.aier.cloud.biz", new Throwable()) + format, args));
			}

			@Override
			protected void logRequest(String configKey, Level logLevel, Request request) {
				req.set(request);
				List<String> args = Lists.newArrayList();
				StringBuilder msg = new StringBuilder();
				msg.append("▷▷▷ %s %s").append("\n");
				args.add(request.method());
				args.add(request.url());
				
				if(logLevel.ordinal() >= Level.HEADERS.ordinal()){
					if(headers){
						msg.append("headers:");
						for(String field : request.headers().keySet()){
							for(String value : valuesOrEmpty(request.headers(), field)){
								msg.append("[ %s=%s ]");
								args.add(field);
								args.add(value);
							}
						}
						msg.append("\n");
					}

					if(request.body() != null){
						if(logLevel.ordinal() >= Level.FULL.ordinal()){
							String bodyText = request.charset() != null ? new String(request.body(), request.charset()) : null;
							msg.append("%s");
							args.add(bodyText+"\n");
						}
					}
					List r = Lists.newArrayList();
					r.add(msg.toString());
					r.add(args);
					logs.set(r);
				}
			}

			@Override
			protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
				List<Object> args = Lists.newArrayList();
				StringBuilder msg = new StringBuilder();

				int status = response.status();
				msg.append("◀◀◀ STATUS:%s 耗时:%s毫秒 服务:%s").append("\n");
				args.add(status);
				args.add(elapsedTime);
				URI asUri = URI.create(req.get().url());
				String clientName = asUri.getHost();
				RetryableFeignLoadBalancer l = (RetryableFeignLoadBalancer)f.create(clientName);
				DynamicServerListLoadBalancer d = (DynamicServerListLoadBalancer)l.getLoadBalancer();
				//只有停止这个监听才能使服务器的状态不受影响
				d.stopServerListRefreshing();
				
				//拿到真正被请求的那个服务地址
				args.add(l.choose(clientName));

				//以下开始组装日志内容,含请求头和请求参数
				if(logLevel.ordinal() >= Level.HEADERS.ordinal()){
					if(headers){
						msg.append("headers:");
						for(String field : response.headers().keySet()){
							for(String value : valuesOrEmpty(response.headers(), field)){
								msg.append("[ %s=%s ]");
								args.add(field);
								args.add(value);
							}
						}
						msg.append("\n");
					}

					int bodyLength = 0;
					if(response.body() != null){
						byte[] bodyData = Util.toByteArray(response.body().asInputStream());
						bodyLength = bodyData.length;
						if(logLevel.ordinal() >= Level.FULL.ordinal() && bodyLength > 0){
							msg.append("%s");
							args.add(new String(bodyData, "UTF-8"));
						}
						List r = logs.get();
						if(r != null){
							msg.insert(0, r.get(0));
							args.addAll(0, (Collection<? extends Object>)r.get(1));
							logs.remove();
						}
						log("", msg.toString(), args.toArray());
						return response.toBuilder().body(bodyData).build();
					} else{
					}
				}
				List r = logs.get();
				if(r != null){
					msg.insert(0, r.get(0));
					args.addAll(0, (Collection<? extends Object>)r.get(1));
					logs.remove();
				}
				req.remove();
				log(configKey, msg.toString(), args.toArray());
				return response;
			}
		};
	}
	
	@Bean
    @Profile({
        "local"
    })
    public IPing getPing() {
        return new AbstractLoadBalancerPing() {
            RestTemplate rt = new RestTemplate();
            @Override
            public void initWithNiwsConfig(IClientConfig arg0) {
            }
            
            @Override
            public boolean isAlive(Server s) {
                
                boolean isLocal = isLocal(s);
                //只对本地服务器去ping,正确响应就说明服务器可用
                if(isLocal) {
                    try {
                        ResponseEntity<String> re = rt.getForEntity("http://" + s.getHostPort() + "/actuator/info", String.class);
                        if(re.getStatusCode() == HttpStatus.OK) {
                            return true;
                        }
                        
                        return false;
                    }catch(Throwable e) {
                        return false;
                    }
                }else {
                    return true;
                }
            }
        };
    }
	
	public static boolean isLocal(Server s) {
		boolean isLocal = s.getHost().indexOf("127.0.0.1") >=0 || s.getHost().indexOf("localhost")>=0;
		return isLocal;
	}

	@Bean
	@Scope("prototype")
	@Profile({
		"local"
	})
	public IRule getRule() {
		return new DevRule();
	}
	
	/**
	 * 优先取本机环境的Rule
	 *
	 * @author xiaokek
	 * @since 2018年6月19日 下午5:20:44
	 */
	public static class DevRule extends ClientConfigEnabledRoundRobinRule{
		Field f = ReflectionUtils.findField(DynamicServerListLoadBalancer.class, "serverListUpdateInProgress");
		@Override
		public Server choose(Object key) {
			ReflectionUtils.makeAccessible(f);
			DynamicServerListLoadBalancer lb = (DynamicServerListLoadBalancer)getLoadBalancer();
	        List<Server> serverList = lb.getAllServers();
	        AtomicBoolean sp = (AtomicBoolean)ReflectionUtils.getField(f, lb);
	        Server mayBe = null;
	        for (Server server: serverList) {
	        	boolean isLocal = isLocal(server);
	        	if(isLocal) {
	        		try {
	        			//DynamicServerListLoadBalancer的updateAllServerList(List<T> ls)会不停更新server的aliveFlag,导致有时对服务的判断不准,特增加对其serverListUpdateInProgress的并发控制,
	        			while(!sp.compareAndSet(false, true)) {
	        			}
		        		if(server.isAlive() && server.isReadyToServe()) {
		        			return server;
		        		}
	        		}finally {
	        			sp.set(false);
	        		}
	        	}else {
	        		mayBe = server;
	        	}
	        }
	        return mayBe;
		}
	};

	
	/**
	 * 给Feign一个独立的编码器，以便禁用BrowserCompatible属性, 避免对医保大量参数解析的问题
	 * @return
	 * @see DefaultFastjsonConfig
	 */
	@Bean
    public Encoder feignEncoder(){
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter() {};
        converter.setFastJsonConfig(fastjsonConfig());
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(converter);
        return new SpringEncoder(objectFactory);
    }
    private FastJsonConfig fastjsonConfig() {
    	FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
        	SerializerFeature.PrettyFormat, //屏蔽这个选项会有问题.....
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteMapNullValue,
            //SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteEnumUsingToString
        );
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setCharset(Charset.forName("utf-8"));
        return fastJsonConfig;
    }

}
