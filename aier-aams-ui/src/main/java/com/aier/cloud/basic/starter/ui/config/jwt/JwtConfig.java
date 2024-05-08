package com.aier.cloud.basic.starter.ui.config.jwt;

import com.aier.cloud.center.common.context.SubjectLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置ui层jwt的用户主体实现类
 *
 * @author xiaokek
 * @since 2018年2月5日 上午9:49:50
 */
@Configuration
@ConditionalOnProperty(prefix = "aier.ui", name = "jwt-open", havingValue = "true")
public class JwtConfig {

    @Bean
    public SubjectLoader uiSubjectLoader(){
    	return new UiSubjectLoaderImpl();
    }
}
