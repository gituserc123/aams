package com.aier.cloud.basic.starter.ui.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * aplatform的参数服务
 *
 * @author xiaokek
 * @since 2019年10月23日 上午10:32:06
 */
@FeignClient(name="aier-service-system")
public interface PlatformConfigService {
    /**
     * 获取该医院配置的帆软版本号
     * @param code
     * @return
     */
    @RequestMapping(value = "/api/sys/platform/getFrVersion", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    String getFrVersion();
    
}
