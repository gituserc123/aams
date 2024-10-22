package com.aier.cloud.biz.aams.controller;

import com.aier.cloud.basic.common.exception.BizAssert;
import com.aier.cloud.biz.aams.entity.Risk;
import com.aier.cloud.biz.aams.entity.RiskScoreStandard;
import com.aier.cloud.biz.aams.service.RiskScoreStandardService;
import com.aier.cloud.biz.aams.service.RiskService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aier.cloud.basic.starter.service.controller.BaseController;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Controller
@RequestMapping("/api/service/biz/aams/riskScoreStandard")
public class RiskScoreStandardController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(RiskScoreStandardController.class);

    @Autowired
    private RiskScoreStandardService riskScoreStandardService;

    @Autowired
    private RiskService riskService;

    @RequestMapping(value = "/queryRiskScoreStandard")
    @ResponseBody
    List<RiskScoreStandard> queryRiskScoreStandard(@RequestParam("riskScoreStandardManualId") Long riskScoreStandardManualId,
                                                   @RequestParam("riskScoreStandardType") String riskScoreStandardType,
                                                   @RequestParam("riskScoreStandardIsDlt") Integer riskScoreStandardIsDlt){
        return riskScoreStandardService.queryRiskScoreStandard(riskScoreStandardManualId,riskScoreStandardType,riskScoreStandardIsDlt);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    Object save(@RequestBody RiskScoreStandard riskScoreStandard){
        BizAssert.isTrue(riskScoreStandard.getRiskScoreStandardManualId() != null, "风险手册Id不能为空！");
        // 根据RiskScoreStandard的riskScoreStandardManualId查询Risk表,确定是否存在SelfRisk
        Risk risk = riskService.selectById(riskScoreStandard.getRiskScoreStandardManualId());
        if(Objects.nonNull(risk.getSelfRiskId()) && risk.getSelfRiskId()>0){
            // 审计手册存在对应的自评手册，需要一起新增或更新对应的自评手册。
            riskScoreStandardService.save(riskScoreStandard);
            try{
                // 重新赋值为自评手册
                riskScoreStandard.setRiskScoreStandardManualId(risk.getSelfRiskId());
                riskScoreStandard.setRiskScoreStandardType("2");
                riskScoreStandard.setRiskScoreStandardId(null);
                riskScoreStandardService.saveSelfRiskScoreStandard(riskScoreStandard);
            }catch (Exception e){
                logger.error("保存自评手册报错！ID为:" + risk.getSelfRiskId(),e);
            }
        }else{
            riskScoreStandardService.save(riskScoreStandard);
        }
        return true;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("id") Long riskScoreStandardId){
        RiskScoreStandard riskScoreStandard = riskScoreStandardService.selectById(riskScoreStandardId);
        // 根据RiskScoreStandard的riskScoreStandardManualId查询Risk表,确定是否存在SelfRisk
        Risk risk = riskService.selectById(riskScoreStandard.getRiskScoreStandardManualId());
        if(Objects.nonNull(risk.getSelfRiskId()) && risk.getSelfRiskId()>0){
            RiskScoreStandard updScore = this.riskScoreStandardService.selectOne(new EntityWrapper<RiskScoreStandard>()
                    .where("RiskScoreStandardManualId={0} and RiskScoreStandardCode={1} and RiskScoreStandardType='2'",
                    risk.getSelfRiskId(), riskScoreStandard.getRiskScoreStandardCode()));
            if(Objects.nonNull(updScore)){
                riskScoreStandardService.deleteRiskScoreStandard(updScore.getRiskScoreStandardId());
            }
        }
        return riskScoreStandardService.deleteRiskScoreStandard(riskScoreStandardId);
    }
}
