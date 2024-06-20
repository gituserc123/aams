package com.aier.cloud.biz.aams.controller;
import com.aier.cloud.biz.aams.entity.SecFunctionality;
import com.aier.cloud.biz.aams.service.SecFunctionalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aier.cloud.basic.starter.service.controller.BaseController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Controller
@RequestMapping("/api/service/biz/aams/secFunctionality")
public class SecFunctionalityController extends BaseController {

    @Autowired
    private SecFunctionalityService secFunctionalityService;

    @RequestMapping("/queryJoinRoles")
    @ResponseBody
    public List<SecFunctionality> queryJoinRoles(@RequestParam("secRoleIds") String secRoleIds){
        return secFunctionalityService.queryJoinRoles(secRoleIds);
    }

}
