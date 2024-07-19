package com.aier.cloud.biz.aams.controller;

import com.aier.cloud.aams.api.request.condition.SelfRiskCondition;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.biz.aams.entity.SelfRisk;
import com.aier.cloud.biz.aams.service.SelfRiskService;
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
@RequestMapping("/api/service/biz/aams/selfRisk")
public class SelfRiskController extends BaseController {

    @Autowired
    private SelfRiskService selfRiskService;

    @RequestMapping(value = "/getSelfRiskById")
    public @ResponseBody SelfRisk getSelfRiskById(@RequestParam("riskId") Long selfRiskId) {
        return selfRiskService.selectById(selfRiskId);
    }

    @RequestMapping(value = "/getAll")
    @ResponseBody
    public PageResponse<SelfRisk> getAll(@RequestBody SelfRiskCondition cond){
        Page<Map<String, Object>> page = tranToPage(cond);
        List<SelfRisk> retLists = selfRiskService.getAll(page,cond);
        return  returnPageResponse(page,retLists);
    }

}
