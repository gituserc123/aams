package com.aier.cloud.biz.aams.controller;


import com.aier.cloud.aams.api.request.condition.AuthorizeAamsCondition;
import com.aier.cloud.basic.api.request.condition.sys.StaffCondition;
import com.aier.cloud.basic.api.response.domain.sys.Institution;
import com.aier.cloud.basic.starter.service.controller.BaseController;
import com.aier.cloud.biz.aams.service.SysStaffInstRoleService;
import com.aier.cloud.biz.feign.InstitutionService;
import com.aier.cloud.biz.feign.StaffService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/service/biz/aams/sysStaff")
public class SysStaffController extends BaseController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private SysStaffInstRoleService sysStaffInstRoleService;

    @PostMapping(value = "/pageByInst")
    public @ResponseBody Object pageByInst(@RequestBody StaffCondition sc) {
        return staffService.pageByInst(sc);
    }

    @PostMapping(value = "/getStaffByCondition")
    public @ResponseBody Object getStaffByCondition(@RequestBody StaffCondition sc){
        Object staffSource = staffService.getAllStaffByCondition(sc);
        if(staffSource!=null){
            JSONObject jObj = (JSONObject)staffSource;
            JSONArray rowsArray = jObj.getJSONArray("rows");
            // 查询用户所有的角色名称
            List<Long> staffIds = rowsArray.stream().map(ra -> {
                JSONObject raObj = (JSONObject)ra;
                return raObj.getLong("ID");
            }).distinct().collect(Collectors.toList());
            AuthorizeAamsCondition cond = new AuthorizeAamsCondition();
            cond.setStaffIds(staffIds);
            List<Map<String, Object>> sirInfos = sysStaffInstRoleService.getStaffRoleInstInfo(cond);
            boolean sirLen = sirInfos.size() > 0;
            /*Map<Long, String> staffRolesMap = Maps.newConcurrentMap();
            if(Objects.nonNull(sirInfos) && sirInfos.size()>0){
                staffRolesMap = sirInfos.stream().collect(Collectors.groupingBy(si -> MapUtils.getLong(si,"staffId"),Collectors.mapping(si -> MapUtils.getString(si,"roleName"),Collectors.joining(", "))));
            }*/
            rowsArray.stream().forEach(instItem -> {
                JSONObject jsonInst = (JSONObject)instItem;
                List<Institution> institutions = institutionService.getDeptListByStaffCodeAndInst(jsonInst.getString("CODE"),jsonInst.getLong("INSTITUTION_ID"));
                String deptName = institutions.stream().map(Institution::getName).collect(Collectors.joining(","));
                jsonInst.put("DEPTNAME",deptName);
                jsonInst.put("Institutions",institutions);
                if(sirLen){
                    String roleNames = sirInfos.stream().filter(si -> MapUtils.getLong(si,"staffId").longValue() == ((JSONObject) instItem).getLong("ID").longValue())
                            .map(si -> MapUtils.getString(si,"roleName")).distinct().collect(Collectors.joining(","));
                    jsonInst.put("roleNames",roleNames);
                }
            });
        }
        return staffSource;
    }

}
