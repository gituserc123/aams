package com.aier.cloud.biz.aams.controller;
import com.aier.cloud.biz.aams.entity.CodeMaster;
import com.aier.cloud.biz.aams.service.CodeMasterService;
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
@RequestMapping("/api/service/biz/aams/codeMaster")
public class CodeMasterController extends BaseController {

    @Autowired
    private CodeMasterService codeMasterService;

    @RequestMapping("/getCodeMasterByType")
    @ResponseBody
    public List<CodeMaster> getCodeMasterByType(@RequestParam("codeMasterType") String codeMasterType){
        return codeMasterService.getCodeMasterByType(codeMasterType);
    }

}
