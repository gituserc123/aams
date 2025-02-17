package com.aier.cloud.ui.biz.aams.controller;

import com.aier.cloud.aams.api.request.condition.AuditRecordCondition;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.ui.biz.aams.feign.AuditPlanFeignService;
import com.aier.cloud.ui.biz.aams.feign.QueryMapperFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
@RequestMapping("/ui/aams/auditplan")
public class AuditPlanUiController extends BaseController {

    private static final String AUDITPLAN_LIST_PAGE = "aams/auditplan/auditplan";

    private static final String AUDIT_ADD_PAGE = "aams/auditplan/addAuditProject";

    @Autowired
    private AuditPlanFeignService auditPlanFeignService;

    @Autowired
    private QueryMapperFeignService queryMapperFeignService;

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Map<String, Object> map, Model model) {

        return AUDITPLAN_LIST_PAGE;
    }

    @RequestMapping(value = "/getAuditPlanList", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public PageResponse<Map<String, Object>> getAuditPlanList(AuditRecordCondition cond) {
        return auditPlanFeignService.getAuditPlanList(cond);
    }

    @RequestMapping(value = "/addAuditProject", method = { RequestMethod.GET, RequestMethod.POST })
    public String addAuditProject(Map<String, Object> map, String inMode, @RequestParam(value = "auditRecordId", required = false) Long auditRecordId) {
        return AUDIT_ADD_PAGE;
    }

}
