package com.aier.cloud.biz.aams.controller;
import com.aier.cloud.biz.aams.entity.RiskFindTemplate;
import com.aier.cloud.biz.aams.service.RiskFindTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aier.cloud.basic.starter.service.controller.BaseController;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Controller
@RequestMapping("/api/service/biz/aams/riskFindTemplate")
public class RiskFindTemplateController extends BaseController {

    @Autowired
    private RiskFindTemplateService riskFindTemplateService;

    @RequestMapping(value = "/getRiskTemplatesByRiskId")
    @ResponseBody
    List<Map<String,Object>> getRiskTemplatesByRiskId(@RequestParam("riskId") Long riskId){
        return riskFindTemplateService.getRiskTemplatesByRiskId(riskId);
    }

    @RequestMapping(value = "/save",method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> save(@RequestBody RiskFindTemplate riskFindTemplate){
        return riskFindTemplateService.save(riskFindTemplate);
    }

}
