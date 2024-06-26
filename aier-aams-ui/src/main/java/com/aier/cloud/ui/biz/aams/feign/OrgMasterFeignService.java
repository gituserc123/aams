package com.aier.cloud.ui.biz.aams.feign;


import com.aier.cloud.aams.api.request.domain.OrgMaster;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "aier-aams-service")
public interface OrgMasterFeignService {


    @RequestMapping("/api/service/biz/aams/orgMaster/getOrgMasterHierarchy")
    @ResponseBody
    List<OrgMaster> getOrgMasterHierarchy(@RequestParam("orgMasterId") String orgMasterId);

}
