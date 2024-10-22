package com.aier.cloud.biz.aams.controller;

import com.aier.cloud.basic.common.exception.BizAssert;
import com.aier.cloud.biz.aams.entity.RiskQuestionStandard;
import com.aier.cloud.biz.aams.service.RiskQuestionStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aier.cloud.basic.starter.service.controller.BaseController;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Controller
@RequestMapping("/api/service/biz/aams/riskQuestionStandard")
public class RiskQuestionStandardController extends BaseController {

    @Autowired
    public RiskQuestionStandardService riskQuestionStandardService;


    @RequestMapping(value = "/queryRiskQuestionStandard")
    @ResponseBody
    List<RiskQuestionStandard> queryRiskQuestionStandard(@RequestParam("riskId") Long riskId,
                                                         @RequestParam("riskQuestionStandardIsDlt") Integer riskQuestionStandardIsDlt){
        return riskQuestionStandardService.queryRiskQuestionStandard(riskId,riskQuestionStandardIsDlt);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    Object save(@RequestBody RiskQuestionStandard riskQuestionStandard){
        BizAssert.isTrue(riskQuestionStandard.getRiskId() != null, "风险手册Id不能为空！");
        return riskQuestionStandardService.save(riskQuestionStandard);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("id") Long riskQuestionStandardId){
        return riskQuestionStandardService.deleteRiskQuestionStandard(riskQuestionStandardId);
    }
}
