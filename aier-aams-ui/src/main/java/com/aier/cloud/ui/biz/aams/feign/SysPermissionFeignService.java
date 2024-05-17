package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.domain.SysPermission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "aier-aams-service")
public interface SysPermissionFeignService {

    @PostMapping(value = "/api/service/biz/aams/sysPermission/getAllPermissions")
    @ResponseBody List<SysPermission> getAllPermissions(@RequestParam(required=false, name="platformCode") String platformCode);

    @PostMapping(value = "/api/service/biz/aams/sysPermission/selectListByUserAndInst")
    @ResponseBody List<SysPermission> selectListByUserAndInst(@RequestParam("staffId") Long staffId, @RequestParam("instId") Long instId, @RequestParam("platformCode") String platformCode);

    @PostMapping(value = "/api/service/biz/aams/sysPermission/lookUpAuthorize")
    List<Map<String, Object>> lookUpAuthorize(@RequestParam("staffId")Long staffId, @RequestParam("instId") Long instId, @RequestParam("platformCode") String platformCode);

}
