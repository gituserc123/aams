package com.aier.cloud.ui.biz.aams.controller;

import com.aier.cloud.basic.api.request.condition.sys.StaffCondition;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.ui.biz.aams.feign.SysStaffFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ui/sys/staff")
public class SysStaffUiController extends BaseController {

    @Autowired
    private SysStaffFeignService staffFeignService;

    @PostMapping(value = "/getAllStaffByCondition")
    public @ResponseBody Object getAllStaffByCondition(StaffCondition sc){
        return staffFeignService.pageByInst(sc);
    }

    @PostMapping(value = "/getStaffByCondition")
    @ResponseBody Object getStaffByCondition(StaffCondition sc){
        return staffFeignService.getStaffByCondition(sc);
    }

    @PostMapping(value = "/getStaffPageByCondition")
    @ResponseBody Object getStaffPageByCondition(StaffCondition sc){
        return staffFeignService.getStaffByCondition(sc);
    }

}
