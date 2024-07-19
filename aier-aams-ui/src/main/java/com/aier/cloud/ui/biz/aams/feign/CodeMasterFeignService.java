package com.aier.cloud.ui.biz.aams.feign;


import com.aier.cloud.aams.api.request.domain.CodeMaster;
import com.aier.cloud.aams.api.request.domain.OrgMaster;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "aier-aams-service")
public interface CodeMasterFeignService {


    @RequestMapping("/api/service/biz/aams/codeMaster/getCodeMasterByType")
    @ResponseBody
    List<CodeMaster> getCodeMasterByType(@RequestParam("codeMasterType") String codeMasterType);

}
