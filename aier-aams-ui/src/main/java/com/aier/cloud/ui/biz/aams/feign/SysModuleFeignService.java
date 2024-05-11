package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.aams.api.request.domain.SysModule;
import com.aier.cloud.basic.api.request.condition.sys.ModuleCondition;
import com.aier.cloud.basic.api.response.domain.sys.Module;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "aier-aams-service")
public interface SysModuleFeignService {

    @PostMapping(value = "/api/service/biz/aams/sysModule/getRoot")
    @ResponseBody SysModule getRoot(@RequestParam("platformCode") String platformCode);

    @PostMapping(value = "/api/service/biz/aams/sysModule/getForTree")
    @ResponseBody List<Map<String, Object>> getForTree(@RequestParam("platformCode") String platformCode);


    @PostMapping(value = "/api/service/biz/aams/sysModule/getPageByParent")
    @ResponseBody PageResponse<Map<String, Object>> getPageByParent(@RequestBody ModuleCondition moduleCondition);

    @PostMapping(value = "/api/service/biz/aams/sysModule/getModulesByPlatform")
    @ResponseBody List<SysModule> getModulesByPlatform(@RequestParam("platformCode") String platformCode);

    /**
     * 获取除根节点外的所有菜单
     * getModules
     * @param platformCode
     * @return List<Module>
     */
    @RequestMapping(value = "/api/service/biz/aams/sysModule/getAllList", method = RequestMethod.POST)
    List<SysModule> getAllList(@RequestParam("platformCode") String platformCode);

    @PostMapping(value = "/api/service/biz/aams/sysModule/getById")
    @ResponseBody SysModule getById(@RequestParam("id") Long id);

    @PostMapping(value = "/api/service/biz/aams/sysModule/getModulePermissionsById")
    @ResponseBody SysModule getModulePermissionsById(@RequestParam("id") Long id);

    @PostMapping(value = "/api/service/biz/aams/sysModule/create")
    @ResponseBody Boolean create(@RequestBody SysModule module);

    @PostMapping(value = "/api/service/biz/aams/sysModule/update")
    @ResponseBody Boolean update(@RequestBody SysModule module);

    @PostMapping(value = "/api/service/biz/aams/sysModule/delete")
    @ResponseBody Boolean delete(@RequestParam("id") Long id);

    @PostMapping(value = "/api/service/biz/aams/sysModule/getCodeUnique")
    @ResponseBody Boolean getCodeUnique(@RequestParam("previousCode") String previousCode, @RequestParam("currentCode") String currentCode);

    @PostMapping(value = "/api/service/biz/aams/sysModule/getNameUnique")
    @ResponseBody Boolean getNameUnique(@RequestParam("previousName") String previousName, @RequestParam("currentName") String currentName);

}
