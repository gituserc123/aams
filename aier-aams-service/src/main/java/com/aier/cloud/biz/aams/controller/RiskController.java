package com.aier.cloud.biz.aams.controller;

import com.aier.cloud.aams.api.request.condition.RiskCondition;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.biz.aams.entity.Risk;
import com.aier.cloud.biz.aams.service.RiskService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aier.cloud.basic.starter.service.controller.BaseController;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/api/service/biz/aams/risk")
public class RiskController extends BaseController {

    @Autowired
    private RiskService riskService;

    @RequestMapping(value = "/getRiskById")
    public @ResponseBody Risk getRiskById(@RequestParam("riskId") Long riskId) {
        return riskService.selectById(riskId);
    }

    @RequestMapping(value = "/getAll")
    @ResponseBody
    public PageResponse<Risk> getAll(@RequestBody RiskCondition cond){
        Page<Map<String, Object>> page = tranToPage(cond);
        List<Risk> retLists = riskService.getAll(page,cond);
        return  returnPageResponse(page,retLists);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String,Object> save(@RequestBody Risk risk){
        return riskService.save(risk);
    }

}
