package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.condition.SecUserCondition;
import com.aier.cloud.aams.api.request.domain.SecUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "aier-aams-service")
public interface SecUserFeignService {

    @RequestMapping("/api/service/biz/aams/secUser/getUserById")
    @ResponseBody
    String getUserById(@RequestParam("userId") Long userId);

    @RequestMapping("/api/service/biz/aams/secUser/getSecUserByCond")
    @ResponseBody
    List<SecUser> getSecUserByCond(@RequestBody SecUserCondition cond);
}
