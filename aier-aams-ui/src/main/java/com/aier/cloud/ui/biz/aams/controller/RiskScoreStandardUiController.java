package com.aier.cloud.ui.biz.aams.controller;

import com.aier.cloud.aams.api.request.domain.RiskScoreStandard;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.ui.biz.aams.feign.RiskScoreStandardFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/aams/riskscorestandard")
public class RiskScoreStandardUiController  extends BaseController {

    @Autowired
    private RiskScoreStandardFeignService riskScoreStandardFeignService;

    @PostMapping(value = "/queryRiskScoreStandard")
    @ResponseBody
    List<RiskScoreStandard> queryRiskScoreStandard(Long riskScoreStandardManualId, String riskScoreStandardType, Integer riskScoreStandardIsDlt){
        return riskScoreStandardFeignService.queryRiskScoreStandard(riskScoreStandardManualId,riskScoreStandardType,riskScoreStandardIsDlt);
    }

    @RequestMapping(value = "/save",method = { RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    Object save(RiskScoreStandard riskScoreStandard){
         riskScoreStandardFeignService.save(riskScoreStandard);
        return success();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    Object delete(@RequestParam("id") Long riskScoreStandardId){
        riskScoreStandardFeignService.delete(riskScoreStandardId);
        return success();
    }

}
