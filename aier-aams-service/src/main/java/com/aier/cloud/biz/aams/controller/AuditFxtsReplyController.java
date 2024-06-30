package com.aier.cloud.biz.aams.controller;

import com.aier.cloud.biz.aams.entity.AuditFxtsReply;
import com.aier.cloud.biz.aams.service.AuditFxtsReplyService;
import com.aier.cloud.biz.aams.service.FxglGroupAuthService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aier.cloud.basic.starter.service.controller.BaseController;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
@RequestMapping("/api/service/biz/aams/auditFxtsReply")
public class AuditFxtsReplyController extends BaseController {


    @Autowired
    private AuditFxtsReplyService auditFxtsReplyService;

    @Autowired
    private FxglGroupAuthService fxglGroupAuthService;

    @RequestMapping("/selectByAuditRecordId")
    @ResponseBody
    public List<Map<String,Object>> selectByAuditRecordId(@RequestParam("auditRecordId") Long auditRecordId){
        List<Map<String,Object>> replys = auditFxtsReplyService.selectByAuditRecordId(auditRecordId);
        replys.stream()
                .filter(re -> MapUtils.getString(re,"orgmastertype").equals("OrgMasterType10"))
                .forEach(re -> {
                    List<Map<String,Object>> fxglGroups = fxglGroupAuthService.selectBySecUserId(MapUtils.getLong(re,"secuserid"));
                    if(Objects.nonNull(fxglGroups) && fxglGroups.size() > 0){
                        String deptMasters = fxglGroups.stream().map(fg -> MapUtils.getString(fg,"deptmastername")).collect(Collectors.joining(","));
                        if(Objects.nonNull(deptMasters) && deptMasters.length()>0){
                            re.put("deptmastername",deptMasters);
                        }
                    }
                });
        return replys;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public boolean save(@RequestBody AuditFxtsReply auditFxtsReply){
        return auditFxtsReplyService.save(auditFxtsReply);
    }
}
