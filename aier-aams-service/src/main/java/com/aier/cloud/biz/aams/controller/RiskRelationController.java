package com.aier.cloud.biz.aams.controller;
import com.aier.cloud.biz.aams.entity.RiskRelation;
import com.aier.cloud.biz.aams.service.RiskRelationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aier.cloud.basic.starter.service.controller.BaseController;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Controller
@RequestMapping("/api/service/biz/aams/riskRelation")
public class RiskRelationController extends BaseController {

    @Autowired
    private RiskRelationService riskRelationService;

    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save")
    public @ResponseBody
    boolean save(@RequestBody RiskRelation riskRelation){
        return riskRelationService.saveRiskRelation(riskRelation);
    }


}
