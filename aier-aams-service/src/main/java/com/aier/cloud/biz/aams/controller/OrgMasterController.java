package com.aier.cloud.biz.aams.controller;
import com.aier.cloud.biz.aams.entity.OrgMaster;
import com.aier.cloud.biz.aams.service.OrgMasterService;
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
@RequestMapping("/api/service/biz/aams/orgMaster")
public class OrgMasterController extends BaseController {

    @Autowired
    private OrgMasterService orgMasterService;


    @RequestMapping("/getOrgMasterHierarchy")
    @ResponseBody
    public List<OrgMaster> getOrgMasterHierarchy(@RequestParam("orgMasterId") String orgMasterId){
        return orgMasterService.getOrgMasterHierarchy(orgMasterId);
    }



}
