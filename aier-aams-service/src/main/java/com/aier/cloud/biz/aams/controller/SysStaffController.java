package com.aier.cloud.biz.aams.controller;


import com.aier.cloud.basic.api.request.condition.sys.StaffCondition;
import com.aier.cloud.basic.api.response.domain.sys.Institution;
import com.aier.cloud.basic.starter.service.controller.BaseController;
import com.aier.cloud.biz.feign.InstitutionService;
import com.aier.cloud.biz.feign.StaffService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/service/biz/aams/sysStaff")
public class SysStaffController extends BaseController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private InstitutionService institutionService;

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
            rowsArray.stream().forEach(instItem -> {
                JSONObject jsonInst = (JSONObject)instItem;
                List<Institution> institutions = institutionService.getDeptListByStaffCodeAndInst(jsonInst.getString("CODE"),jsonInst.getLong("INSTITUTION_ID"));
                String deptName = institutions.stream().map(Institution::getName).collect(Collectors.joining(","));
                jsonInst.put("DEPTNAME",deptName);
            });
        }
        return staffSource;
    }

}
