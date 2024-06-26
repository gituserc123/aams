package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.condition.AuditRecordCondition;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@FeignClient(name = "aier-aams-service")
public interface AuditRecordFeignService {

    @RequestMapping(value = "/api/service/biz/aams/auditRecord/getAllFxts")
    @ResponseBody
    PageResponse<Map<String, Object>> getAllFxts(@RequestBody AuditRecordCondition cond);
}
