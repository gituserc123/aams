package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.domain.SysPermission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "aier-aams-service")
public interface SysPermissionFeignService {

    @PostMapping(value = "/api/service/biz/aams/sysPermission/getAllPermissions")
    @ResponseBody List<SysPermission> getAllPermissions(@RequestParam(required=false, name="platformCode") String platformCode);

    @PostMapping(value = "/api/service/biz/aams/sysPermission/selectListByUserAndInst")
    @ResponseBody List<SysPermission> selectListByUserAndInst(@RequestParam("staffId") Long staffId, @RequestParam("instId") Long instId, @RequestParam("platformCode") String platformCode);

    @RequestMapping(value = "/api/service/biz/aams/sysPermission/lookUpAuthorize", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    List<Map<String, Object>> lookUpAuthorize(@RequestParam("staffId")Long staffId, @RequestParam("instId") Long instId, @RequestParam("platformCode") String platformCode);

}
