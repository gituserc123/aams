package com.aier.cloud.biz.aams.controller;
import com.aier.cloud.biz.aams.entity.SecRole;
import com.aier.cloud.biz.aams.service.SecRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aier.cloud.basic.starter.service.controller.BaseController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Controller
@RequestMapping("/api/service/biz/aams/secRole")
public class SecRoleController extends BaseController {

    @Autowired
    private SecRoleService secRoleService;


    @RequestMapping("/queryRoleByUserId")
    @ResponseBody
    List<SecRole> queryRoleByUserId(@RequestParam("secUserId") Long secUserId){
        return secRoleService.queryRoleByUserId(secUserId);
    }

}
