package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.domain.RiskQuestionStandard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@FeignClient(name = "aier-aams-service")
public interface RiskQuestionStandardFeignService {

    @RequestMapping(value = "/api/service/biz/aams/riskQuestionStandard/queryRiskQuestionStandard")
    @ResponseBody
    List<RiskQuestionStandard> queryRiskQuestionStandard(@RequestParam("riskId") Long riskId,
                                                         @RequestParam("riskQuestionStandardIsDlt") Integer riskQuestionStandardIsDlt);

    @RequestMapping(value = "/api/service/biz/aams/riskQuestionStandard/save")
    @ResponseBody
    Object save(@RequestBody RiskQuestionStandard riskQuestionStandard);

    @RequestMapping(value = "/api/service/biz/aams/riskQuestionStandard/delete")
    @ResponseBody
    Object delete(@RequestParam("id") Long riskQuestionStandardId);

}
