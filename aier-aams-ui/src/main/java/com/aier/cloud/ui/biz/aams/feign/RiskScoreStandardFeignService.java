package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.domain.RiskScoreStandard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "aier-aams-service")
public interface RiskScoreStandardFeignService {

    @RequestMapping(value = "/api/service/biz/aams/riskScoreStandard/queryRiskScoreStandard")
    @ResponseBody
    List<RiskScoreStandard> queryRiskScoreStandard(@RequestParam("riskScoreStandardManualId") Long riskScoreStandardManualId,
                                                   @RequestParam("riskScoreStandardType") String riskScoreStandardType,
                                                   @RequestParam("riskScoreStandardIsDlt") Integer riskScoreStandardIsDlt);

    @RequestMapping(value = "/api/service/biz/aams/riskScoreStandard/save")
    Object save(@RequestBody RiskScoreStandard riskScoreStandard);

    @RequestMapping(value = "/api/service/biz/aams/riskScoreStandard/delete")
    @ResponseBody
    Object delete(@RequestParam("id") Long riskScoreStandardId);
}
