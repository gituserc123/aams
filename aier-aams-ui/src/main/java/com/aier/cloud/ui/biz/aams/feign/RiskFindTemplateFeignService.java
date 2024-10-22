package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.domain.RiskFindTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "aier-aams-service")
public interface RiskFindTemplateFeignService {

    @RequestMapping(value = "/api/service/biz/aams/riskFindTemplate/getRiskTemplatesByRiskId")
    @ResponseBody
    List<Map<String,Object>> getRiskTemplatesByRiskId(@RequestParam("riskId") Long riskId);

    @RequestMapping(value = "/api/service/biz/aams/riskFindTemplate/save",method = {RequestMethod.POST })
    @ResponseBody
    Map<String, Object> save(@RequestBody RiskFindTemplate riskFindTemplate);
}
