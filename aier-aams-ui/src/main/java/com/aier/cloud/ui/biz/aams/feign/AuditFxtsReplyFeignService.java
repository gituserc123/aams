package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.domain.AuditFxtsReply;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "aier-aams-service")
public interface AuditFxtsReplyFeignService {

    @RequestMapping("/api/service/biz/aams/auditFxtsReply/selectByAuditRecordId")
    @ResponseBody
    List<Map<String,Object>> selectByAuditRecordId(@RequestParam("auditRecordId") Long auditRecordId);

    @RequestMapping(value = "/api/service/biz/aams/auditFxtsReply/save")
    @ResponseBody
    boolean save(@RequestBody AuditFxtsReply auditFxtsReply);

}
