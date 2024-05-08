package com.aier.cloud.ui.biz.aams.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "aier-aams-service")
public interface UserInfoTestFeignService {

    @RequestMapping(value = "/api/service/biz/aams/test/testGetShiroUser")
    @ResponseBody
    String testGetShiroUser(@RequestParam("testStr") String testStr);
}
