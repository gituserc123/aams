package com.aier.cloud.biz.common.controller;


import com.aier.cloud.center.common.context.AierUser;
import com.aier.cloud.center.common.context.UserContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api/service/biz/aams/test")
public class UserInfoTestController {

    @RequestMapping(value = "/testGetShiroUser")
    @ResponseBody
    public String testGetShiroUser(@RequestParam("testStr") String testStr){
        AierUser aamsUser = UserContext.getUser();
        return testStr + "---" + aamsUser.getMasterSlaveCookie().get("userName") + ":" + aamsUser.getMasterSlaveCookie().get("userAge");
    }
}
