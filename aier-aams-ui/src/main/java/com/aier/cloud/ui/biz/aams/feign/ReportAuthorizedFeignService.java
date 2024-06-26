package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.domain.ReportAuthorized;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "aier-aams-service")
public interface ReportAuthorizedFeignService {

    @RequestMapping("/api/service/biz/aams/reportAuthorized/selectByAuditRecordId")
    @ResponseBody
    List<ReportAuthorized> selectByAuditRecordId(@RequestParam("auditRecordId") Long auditRecordId,@RequestParam(name="secUserId",required = false) Long secUserId,
                                                 @RequestParam(name="reportAuthorizedIsdlt",required = false) Boolean reportAuthorizedIsdlt);

    @RequestMapping(value = "/api/service/biz/aams/reportAuthorized/save")
    @ResponseBody
    boolean save(@RequestBody ReportAuthorized reportAuthorized);


}
