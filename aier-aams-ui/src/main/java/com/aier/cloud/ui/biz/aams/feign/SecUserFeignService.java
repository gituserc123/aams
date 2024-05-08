package com.aier.cloud.ui.biz.aams.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "aier-aams-service")
public interface SecUserFeignService {

    @RequestMapping("/api/service/biz/aams/secUser/getUserById")
    @ResponseBody
    String getUserById(@RequestParam("userId") Long userId);
}
