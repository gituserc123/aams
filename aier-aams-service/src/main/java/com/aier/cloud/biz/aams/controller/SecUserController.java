package com.aier.cloud.biz.aams.controller;
import org.springframework.stereotype.Controller;
import com.aier.cloud.basic.starter.service.controller.BaseController;

import cn.hutool.json.JSONUtil;
import com.aier.cloud.biz.aams.entity.SecUser;
import com.aier.cloud.biz.aams.service.SecUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/service/biz/aams/secUser")
public class SecUserController extends BaseController {

    @Autowired
    private SecUserService secUserService;

    @RequestMapping("/getUserById")
    @ResponseBody
    public String getUserById(@RequestParam("userId") Long userId) {
        SecUser secUser = secUserService.selectByPrimaryKey(userId);
        return JSONUtil.toJsonStr(secUser);
    }

}
