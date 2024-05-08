package com.aier.cloud.ui.biz.aams.controller;


import com.aier.cloud.ui.biz.aams.feign.UserInfoTestFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/ui/aams/manage/test")
public class UserInfoTestUiController {

    @Autowired
    private UserInfoTestFeignService userInfoTestUiFeignService;

    @RequestMapping(value = "/testGetShiroUser",method = { RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String testGetShiroUser(){
        return userInfoTestUiFeignService.testGetShiroUser("测试112");
    }
}
