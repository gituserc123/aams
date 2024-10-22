package com.aier.cloud.ui.biz.aams.controller;


import com.aier.cloud.aams.api.request.domain.RiskQuestionStandard;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.ui.biz.aams.feign.RiskQuestionStandardFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/ui/aams/riskquestionstandard")
public class RiskQuestionStandardUiController  extends BaseController {

    @Autowired
    private RiskQuestionStandardFeignService riskQuestionStandardFeignService;

    @RequestMapping(value = "/queryRiskQuestionStandard")
    @ResponseBody
    List<RiskQuestionStandard> queryRiskQuestionStandard(Long riskId,Integer riskQuestionStandardIsDlt){
        return riskQuestionStandardFeignService.queryRiskQuestionStandard(riskId,riskQuestionStandardIsDlt);
    }

    @RequestMapping(value = "/save",method = { RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    Object save(RiskQuestionStandard riskQuestionStandard){
        riskQuestionStandardFeignService.save(riskQuestionStandard);
        return success();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    Object delete(@RequestParam("id") Long riskQuestionStandardId){
        riskQuestionStandardFeignService.delete(riskQuestionStandardId);
        return success();
    }

}
