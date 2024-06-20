package com.aier.cloud.biz.aams.controller;
import com.aier.cloud.biz.aams.entity.DeptMaster;
import com.aier.cloud.biz.aams.service.DeptMasterService;
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
 * @since 2024-04-20 11:47:37
 */
@Controller
@RequestMapping("/api/service/biz/aams/deptMaster")
public class DeptMasterController extends BaseController {

    @Autowired
    private DeptMasterService deptMasterService;


    @RequestMapping("/getDepartmentHierarchy")
    @ResponseBody
    public List<DeptMaster> getDepartmentHierarchy(@RequestParam("deptMasterCode") String deptMasterCode){
        return deptMasterService.getDepartmentHierarchy(deptMasterCode);
    }
}
