package com.aier.cloud.biz.aams.controller;

import com.aier.cloud.aams.api.request.condition.AuditRecordCondition;
import com.aier.cloud.biz.aams.entity.AuditRecord;
import com.aier.cloud.biz.aams.service.AuditRecordService;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aier.cloud.basic.starter.service.controller.BaseController;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/api/service/biz/aams/auditRecord")
public class AuditRecordController extends BaseController {

    @Autowired
    private AuditRecordService auditRecordService;


    @ApiOperation(value = "条件查询详情")
    @RequestMapping(value = "/getAllFxts")
    @ResponseBody
    public PageResponse<AuditRecord> getAllFxts(@RequestBody AuditRecordCondition cond){
        Page<Map<String, Object>> page = tranToPage(cond);
        List<AuditRecord> retLists = auditRecordService.getAllFxts(page,cond);
        return  returnPageResponse(page,retLists);
    }

}
