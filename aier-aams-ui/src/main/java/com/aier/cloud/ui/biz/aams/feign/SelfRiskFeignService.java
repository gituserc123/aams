package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.condition.SelfRiskCondition;
import com.aier.cloud.aams.api.request.domain.SelfRisk;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "aier-aams-service")
public interface SelfRiskFeignService {

    @RequestMapping(value = "/api/service/biz/aams/selfRisk/getSelfRiskById")
    @ResponseBody SelfRisk getSelfRiskById(@RequestParam("riskId") Long selfRiskId);

    @RequestMapping(value = "/api/service/biz/aams/selfRisk/getAll")
    @ResponseBody
    PageResponse<SelfRisk> getAll(@RequestBody SelfRiskCondition cond);
}
