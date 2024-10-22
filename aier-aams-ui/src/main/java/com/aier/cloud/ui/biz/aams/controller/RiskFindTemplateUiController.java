package com.aier.cloud.ui.biz.aams.controller;

import com.aier.cloud.aams.api.request.domain.RiskFindTemplate;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.ui.biz.aams.feign.RiskFindTemplateFeignService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ui/aams/riskTemplate")
public class RiskFindTemplateUiController  extends BaseController {

    @Autowired
    private RiskFindTemplateFeignService riskFindTemplateFeignService;

    @PostMapping(value = "/getRiskTemplatesByRiskId")
    @ResponseBody
    List<Map<String,Object>> getRiskTemplatesByRiskId(Long riskId){
        return riskFindTemplateFeignService.getRiskTemplatesByRiskId(riskId);
    }

    @RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public  Map<String, Object> save(@RequestBody RiskFindTemplate riskFindTemplate){
        return riskFindTemplateFeignService.save(riskFindTemplate);
    }

}
