package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.condition.RiskCondition;
import com.aier.cloud.aams.api.request.domain.Risk;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@FeignClient(name = "aier-aams-service")
public interface RiskFeignService {

    @RequestMapping(value = "/api/service/biz/aams/risk/getRiskById")
    @ResponseBody Risk getRiskById(@RequestParam("riskId") Long riskId);

    @RequestMapping(value = "/api/service/biz/aams/risk/getAll")
    @ResponseBody
    PageResponse<Risk> getAll(@RequestBody RiskCondition cond);

    @RequestMapping(value = "/api/service/biz/aams/risk/save")
    @ResponseBody
    Map<String,Object> save(@RequestBody Risk risk);
}
