package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.condition.AuthorizeAamsCondition;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "aier-aams-service")
public interface AuthorizeFeignService {

    @PostMapping(value = "/api/sys/authorize/getRoleForTree")
    List<Map<String,Object>> getRoleForTree(@RequestParam("platformCode") String platformCode);


    @PostMapping(value = "/api/sys/authorize/getRoleTreeByStaffInst")
    List<Map<String,Object>> getRoleTreeByStaffInst(@RequestParam("staffId") Long staffId, @RequestParam("instId") Long instId, @RequestParam("platformCode") String platformCode);

    @PostMapping(value = "/api/sys/authorize/getListByRoleAuthorize")
    PageResponse<Map<String,Object>> getListByRoleAuthorize(@RequestBody AuthorizeAamsCondition authorizeCondition);

    @PostMapping(value = "/api/sys/authorize/getListNotAuthorize")
    List<Map<String,Object>> getListNotAuthorize(@RequestBody AuthorizeAamsCondition authorizeCondition);

}
