package com.aier.cloud.biz.aams.controller;

import com.aier.cloud.aams.api.domain.enums.OrgCapabilityEnum;
import com.aier.cloud.aams.api.request.condition.AuditRecordCondition;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.basic.starter.service.controller.BaseController;
import com.aier.cloud.biz.aams.entity.AuditRecord;
import com.aier.cloud.biz.aams.entity.AuditRecordPerson;
import com.aier.cloud.biz.aams.entity.SecUser;
import com.aier.cloud.biz.aams.service.AuditRecordPersonService;
import com.aier.cloud.biz.aams.service.AuditRecordService;
import com.aier.cloud.biz.aams.service.SecUserService;
import com.aier.cloud.biz.common.config.JdbcHelper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Controller
@RequestMapping("/api/service/biz/aams/auditPlan")
public class AuditPlanController extends BaseController {

    @Autowired
    private AuditRecordService auditRecordService;

    @Autowired
    private AuditRecordPersonService auditRecordPersonService;

    @Autowired
    private SecUserService secUserService;

    @Resource(name = "aierViewJdbcHelper")
    private JdbcHelper db;

    @ApiOperation(value = "条件查询详情")
    @RequestMapping(value = "/getAuditPlanList")
    @ResponseBody
    public PageResponse<AuditRecord> getAuditPlanList(@RequestBody AuditRecordCondition cond){
        Page<Map<String, Object>> page = tranToPage(cond);
        List<AuditRecord> retLists = auditRecordService.getAuditPlanList(page,cond);
        List<Long> auditRecordIds = retLists.stream().map(AuditRecord::getAuditRecordId).collect(Collectors.toList());
        // 项目人员查询
        EntityWrapper<AuditRecordPerson> ew = new EntityWrapper<>();
        ew.in("AuditRecordId", auditRecordIds);
        List<AuditRecordPerson> auditRecordPersonList = auditRecordPersonService.selectList(ew);
        Map<Long, List<AuditRecordPerson>> auditRecordPersonMap = auditRecordPersonList.stream().collect(Collectors.groupingBy(AuditRecordPerson::getAuditRecordId));
        List<Long> secUserIds = auditRecordPersonList.stream().map(AuditRecordPerson::getSecUserId).distinct().collect(Collectors.toList());
        EntityWrapper<SecUser> ewUser = new EntityWrapper<>();
        ewUser.in("SecUserId", secUserIds);
        List<SecUser> secUserList = secUserService.selectList(ewUser);
        Map<Long,String> auditPersons = Maps.newConcurrentMap();
        auditRecordPersonMap.keySet().stream().forEach(auditRecordId -> {
            List<AuditRecordPerson> auditRecordPersons = auditRecordPersonMap.get(auditRecordId);
            String auditPerson = auditRecordPersons.stream().map(auditRecordPerson -> {
                SecUser secUser = secUserList.stream().filter(user -> user.getSecUserId().equals(auditRecordPerson.getSecUserId())).findFirst().get();
                if(auditRecordPerson.getAuditRecordPersonIsManager()){
                    if(auditRecordPerson.getAuditRecordPersonLx().intValue()==1){
                        return secUser.getSecUserName()+"(主/质)";
                    }else{
                        return secUser.getSecUserName()+"(主)";
                    }
                }else{
                    if(auditRecordPerson.getAuditRecordPersonLx().intValue()==1){
                        return secUser.getSecUserName()+"(质)";
                    }else{
                        return secUser.getSecUserName();
                    }
                }
            }).collect(Collectors.joining(","));
            auditPersons.put(auditRecordId,auditPerson);
        });
        retLists.stream().forEach(auditRecord -> {
            // 机构级别
            Optional<OrgCapabilityEnum> oceDesc = Arrays.stream(OrgCapabilityEnum.values())
                    .filter(oce -> (auditRecord.getAuditRecordCapability().intValue() == oce.getValue()))
                    .findFirst();
            if(oceDesc.isPresent()){
                auditRecord.setAuditRecordCapabilityDesc(oceDesc.get().getContent());
            }
            // 项目人员
            if(auditPersons.containsKey(auditRecord.getAuditRecordId())){
                auditRecord.setAuditPlanPerson(auditPersons.get(auditRecord.getAuditRecordId()));
            }
        });
        return  returnPageResponse(page,retLists);
    }
}
