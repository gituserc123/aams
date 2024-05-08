package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.domain.SysRole;
import com.aier.cloud.basic.api.request.condition.sys.RoleCondition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "aier-aams-service")
public interface SysRoleFeignService {


    @PostMapping(value = "/api/service/biz/aams/sysRole/getForTree")
    @ResponseBody List getForTree(@RequestBody SysRole r);

    @PostMapping(value = "/api/service/biz/aams/sysRole/getPermByRoleId")
    @ResponseBody
    List getPermByRoleId(@RequestParam(value="roleId") Long roleId,
                         @RequestParam(value="isEdit", required=false, defaultValue="true") boolean isEdit, @RequestParam(value="platformCode") String platformCode);

    @PostMapping(value = "/api/service/biz/aams/sysRole/updateRolePerm")
    @ResponseBody void updateRolePerm(@RequestBody RoleCondition rc);

    @PostMapping(value = "/api/service/biz/aams/sysRole/update")
    @ResponseBody void update(@RequestBody SysRole role);

    @PostMapping(value = "/api/service/biz/aams/sysRole/delete")
    @ResponseBody void delete(@RequestParam(value="roleId") Long roleId);

    @PostMapping(value = "/api/service/biz/aams/sysRole/getById")
    @ResponseBody SysRole getById(@RequestParam(value="id") Long id);

    @PostMapping(value = "/api/service/biz/aams/sysRole/selectRolesByStaff")
    @ResponseBody
    List<String> selectRolesByStaff(@RequestParam(value="staffId") Long staffId, @RequestParam(value="instId") Long instId);

}
