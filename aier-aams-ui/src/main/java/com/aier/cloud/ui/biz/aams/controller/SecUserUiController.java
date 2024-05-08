package com.aier.cloud.ui.biz.aams.controller;


import com.aier.cloud.ui.biz.aams.feign.SecUserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/ui/aams/manage/secuser")
public class SecUserUiController {

    @Autowired
    private SecUserFeignService secUserFeignService;

    @RequestMapping(value = "/getUserById",method = { RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    String getUserById(Long userId){
        return secUserFeignService.getUserById(userId);
    }
}
