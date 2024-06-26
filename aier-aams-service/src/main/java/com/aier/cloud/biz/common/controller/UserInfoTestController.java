package com.aier.cloud.biz.common.controller;

import com.aier.cloud.aams.api.request.domain.DeptMaster;
import com.aier.cloud.aams.api.request.domain.SecFunctionality;
import com.aier.cloud.aams.api.request.domain.SecRole;
import com.aier.cloud.aams.api.request.domain.SecUser;
import com.aier.cloud.biz.aams.entity.OrgMaster;
import com.aier.cloud.center.common.context.AierUser;
import com.aier.cloud.center.common.context.UserContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/api/service/biz/aams/test")
public class UserInfoTestController {

    @RequestMapping(value = "/testGetShiroUser")
    @ResponseBody
    public String testGetShiroUser(@RequestParam("testStr") String testStr){
        AierUser aamsUser = UserContext.getUser();
        SecUser secUser = JSON.parseObject(aamsUser.getMasterSlaveCookie().get("secUser"), SecUser.class);
        List<SecRole> auditRoles = JSONArray.parseArray(aamsUser.getMasterSlaveCookie().get("auditRoles"), SecRole.class);
        List<DeptMaster> deptMasters = JSONArray.parseArray(aamsUser.getMasterSlaveCookie().get("deptMasters"),DeptMaster.class);
        List<OrgMaster> orgMasters = JSONArray.parseArray(aamsUser.getMasterSlaveCookie().get("orgMasters"),OrgMaster.class);
        //List<SecFunctionality> secFunctionalities = JSONArray.parseArray(aamsUser.getMasterSlaveCookie().get("secFunctionalities"),SecFunctionality.class);

        return testStr + "---:" + JSON.toJSONString(secUser) + "<p>"  + JSON.toJSONString(auditRoles)  + "<p>" +  JSON.toJSONString(deptMasters) + "<p>" +  JSON.toJSONString(orgMasters);
    }
}
