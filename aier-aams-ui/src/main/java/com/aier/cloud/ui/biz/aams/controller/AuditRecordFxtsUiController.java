package com.aier.cloud.ui.biz.aams.controller;

import com.aier.cloud.aams.api.request.condition.AuditRecordCondition;
import com.aier.cloud.aams.api.request.condition.SecUserCondition;
import com.aier.cloud.aams.api.request.domain.ReportAuthorized;
import com.aier.cloud.aams.api.request.domain.SecUser;
import com.aier.cloud.basic.api.request.condition.sys.StaffCondition;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.ui.biz.aams.feign.AuditRecordFeignService;
import com.aier.cloud.ui.biz.aams.feign.ReportAuthorizedFeignService;
import com.aier.cloud.ui.biz.aams.feign.SecUserFeignService;
import com.aier.cloud.ui.biz.aams.feign.SysStaffFeignService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/ui/aams/auditRecordFxts")
public class AuditRecordFxtsUiController extends BaseController {

    @Autowired
    private AuditRecordFeignService auditRecordFeignService;

    @Autowired
    private ReportAuthorizedFeignService reportAuthorizedFeignService;

    @Autowired
    private SecUserFeignService secUserFeignService;

    @Autowired
    private SysStaffFeignService staffFeignService;

    private static final String AUDIT_FXTS_HOME = "aams/fxts/auditfxtshome";
    private static final String AUDIT_FXTS_AUDITREPORTAUTHORIZE = "aams/fxts/auditReportAuthorize";

    @RequestMapping(value = "/auditfxtshome", method = { RequestMethod.GET, RequestMethod.POST })
    public String adaList(Map<String, Object> map) {
        return AUDIT_FXTS_HOME;
    }

    @RequestMapping(value = "/getAllFxts", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public PageResponse<Map<String, Object>> getAllFxts(AuditRecordCondition cond){
        return auditRecordFeignService.getAllFxts(cond);
    }

    @RequestMapping(value = "/auditReportAuthorize", method = { RequestMethod.GET, RequestMethod.POST })
    public String auditReportAuthorize(Long auditRecordId, Model model) {
        model.addAttribute("auditRecordId", auditRecordId);
        return AUDIT_FXTS_AUDITREPORTAUTHORIZE;
    }

    @RequestMapping(value = "/selectByAuditRecordId", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    List<ReportAuthorized> selectByAuditRecordId(Long auditRecordId){
        return reportAuthorizedFeignService.selectByAuditRecordId(auditRecordId,null,false);
    }

    @PostMapping(value = "/getStaffByCondition")
    @ResponseBody Object getStaffByCondition(StaffCondition sc,Long auditRecordId){
        System.out.println(auditRecordId);
        List<ReportAuthorized> reportAuthorizeds = reportAuthorizedFeignService.selectByAuditRecordId(auditRecordId,null,false);
        Object staffs = staffFeignService.getStaffByCondition(sc);
        return staffs;
    }

    @PostMapping(value = "/saveReportAuthorized")
    @ResponseBody Object saveReportAuthorized(ReportAuthorized ra){
        Map<String,Object> result = Maps.newHashMap();
        if(ra.getReportAuthorizedId()!=null && ra.getReportAuthorizedId().intValue() > 0
                && ra.getSecUserId()!=null && ra.getSecUserId().longValue()>0){
            ra.setReportAuthorizedIsdlt(true);
        }else{
            Long secUserId;
            // 1.根据工号SecUserMainCode和deptMasterCode科室/部门查询SecUser表，获取SecUserId
            SecUserCondition cond = new SecUserCondition();
            cond.setSecUserMainCode(ra.getSecUserMainCode());
            cond.setDeptMasterCode(ra.getDeptMasterCode());
            List<SecUser> secUsers = secUserFeignService.getSecUserByCond(cond);
            if(Objects.nonNull(secUsers) && secUsers.size()>0){
                secUserId=secUsers.get(0).getSecuserid();
            }else{
                result.put("code",500);
                result.put("msg","根据工号和部门ID未查询到用户！");
                return result;
            }
            // 2.根据SecUserId和auditRecordId查询ReportAuthorized是否存在记录
            if(Objects.nonNull(secUserId) && secUserId.intValue()!=0){
                List<ReportAuthorized> reportAuthorizeds = reportAuthorizedFeignService.selectByAuditRecordId(ra.getAuditRecordId(),secUserId,null);
                if(Objects.nonNull(reportAuthorizeds) && reportAuthorizeds.size()>0){
                    ra.setReportAuthorizedId(reportAuthorizeds.get(0).getReportAuthorizedId());
                }
                ra.setSecUserId(secUserId);
            }
            ra.setReportAuthorizedIsdlt(false);
        }
        if(reportAuthorizedFeignService.save(ra)){
            result.put("code",200);
            result.put("msg","授权成功！");
        }else{
            result.put("code",300);
            result.put("msg","授权失败！");
        }

        return result;
    }

}
