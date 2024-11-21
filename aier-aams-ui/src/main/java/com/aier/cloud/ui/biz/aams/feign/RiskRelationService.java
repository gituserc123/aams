package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.domain.RiskRelation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "aier-aams-service")
public interface RiskRelationService {

    @RequestMapping(value = "/api/service/biz/aams/riskRelation/save")
    @ResponseBody
    boolean save(@RequestBody RiskRelation riskRelation);
}
