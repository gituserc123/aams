package com.aier.cloud.biz.aams.controller;
import com.aier.cloud.biz.aams.entity.ReportAuthorized;
import com.aier.cloud.biz.aams.service.ReportAuthorizedService;
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
@RequestMapping("/api/service/biz/aams/reportAuthorized")
public class ReportAuthorizedController extends BaseController {

    @Autowired
    private ReportAuthorizedService reportAuthorizedService;

    @RequestMapping("/selectByAuditRecordId")
    @ResponseBody
    List<ReportAuthorized> selectByAuditRecordId(@RequestParam("auditRecordId") Long auditRecordId,@RequestParam(name="secUserId",required = false) Long secUserId,
                                                 @RequestParam(name="reportAuthorizedIsdlt",required = false) Boolean reportAuthorizedIsdlt){
        return reportAuthorizedService.selectByAuditRecordId(auditRecordId,secUserId,reportAuthorizedIsdlt);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public boolean save(@RequestBody ReportAuthorized reportAuthorized){
        return reportAuthorizedService.save(reportAuthorized);
    }

}
