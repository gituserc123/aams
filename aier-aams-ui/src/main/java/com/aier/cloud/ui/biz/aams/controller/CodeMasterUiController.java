package com.aier.cloud.ui.biz.aams.controller;


import com.aier.cloud.aams.api.request.domain.CodeMaster;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.ui.biz.aams.feign.CodeMasterFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/ui/aams/codeMaster")
public class CodeMasterUiController   extends BaseController {

    @Autowired
    private CodeMasterFeignService codeMasterFeignService;

    @RequestMapping(value = "/getCodeMasterByType", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    protected List<CodeMaster>  getCodeMasterByType(String codeMasterType){
        List<CodeMaster> ldLists = codeMasterFeignService.getCodeMasterByType(codeMasterType);
        return ldLists;
    }
}
