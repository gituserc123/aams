package com.aier.cloud.ui.biz.aams.controller;

import com.aier.cloud.aams.api.request.domain.SysModule;
import com.aier.cloud.aams.api.request.domain.SysPermission;
import com.aier.cloud.basic.api.domain.enums.InstEnum;
import com.aier.cloud.basic.api.request.condition.sys.ModuleCondition;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.basic.api.response.domain.sys.Institution;
import com.aier.cloud.ui.biz.sys.service.InstitutionService;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.basic.common.properties.AierUiProperties;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import com.aier.cloud.ui.biz.aams.feign.SysModuleFeignService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping("/ui/sys/module")
public class SysModuleUiController  extends BaseController {

    @Autowired
    private SysModuleFeignService sysModuleFeignService;
    @Autowired
    private InstitutionService instService;
    @Autowired
    private AierUiProperties aierUiProperties;
    // 测试Git提交
    private static final String CREATE = "sys/module/create";
    private static final String UPDATE = "sys/module/update";
    private static final String LIST = "sys/module/list";
    private static final String VIEW = "sys/module/view";

    @RequiresPermissions("Module:save")
    @GetMapping(value="/create/{parentModuleId}/{platformCode}")
    public String preCreate(Map<String, Object> map, @PathVariable String parentModuleId, @PathVariable String platformCode) {
        map.put("parentModuleId", parentModuleId);
        map.put("platformCode", platformCode);
        return CREATE;
    }

    @RequiresPermissions("Module:save")
    @PostMapping(value="/create")
    public @ResponseBody Map<String,Object> create(SysModule module) {
        SysModule parentModule = sysModuleFeignService.getById(module.getParent().getId());
        if (parentModule == null) {
            return fail("添加模块失败：id=" + module.getParent().getId() + "的父模块不存在！");
        }
        module.setParent(parentModule);
        module.setModifer(ShiroUtils.getId());
        //module.setPlatformCode(Optional.ofNullable(module.getPlatformCode()).orElse(aierUiProperties.getSiteCode()));
        String message = hasValid(module);
        if(StringUtils.isNotEmpty(message))  {
            return fail(message);
        }

        List<SysPermission> permissions = Lists.newArrayList();
        for (SysPermission permission : module.getPermissions()) {
            if (StringUtils.isNotBlank(permission.getPermCode())) {
                permission.setModifer(ShiroUtils.getId());
                permissions.add(permission);
            }
        }
        module.setPermissions(permissions);
        try {
            sysModuleFeignService.create(module);
        } catch (BizException e) {
            return fail("添加模块失败：" + e.getMessage());
        }

        return success("添加模块成功！");
    }

    @RequiresPermissions("Module:edit")
    @GetMapping(value="/update/{id}")
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        SysModule module = sysModuleFeignService.getModulePermissionsById(id);
        map.put("module", module);
        return UPDATE;
    }

    @RequiresPermissions("Module:edit")
    @PostMapping(value="/update")
    public @ResponseBody Map<String,Object> update(SysModule module) {
        module.setModifer(ShiroUtils.getId());
        String message = hasValid(module);
        if(StringUtils.isNotEmpty(message))  {
            return fail(message);
        }
        for (SysPermission permission : module.getPermissions()) {
            if (StringUtils.isNotBlank(permission.getPermCode())) {
                permission.setModifer(ShiroUtils.getId());
            }
        }
        sysModuleFeignService.update(module);
        return success("修改模块成功！");
    }

    @RequiresPermissions("Module:view")
    @RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
    public String list(Map<String, Object> map, String platformCode) {
        //默认是平台菜单
        map.put("rootModule", sysModuleFeignService.getRoot(aierUiProperties.getSiteCode()));
        Map<String,Object> platMap = Maps.newHashMap();
        platMap.put("code","aams");
        platMap.put("name","aams审计");
        List<Map<String, Object>> mapList = Lists.newArrayList();
        mapList.add(platMap);
        map.put("platforms", mapList);
        return LIST;
    }


    @RequiresPermissions("Module:view")
    @RequestMapping(value="/tree", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody List<Map<String, Object>> tree(Map<String, Object> map, String platformCode) {
        List<Map<String, Object>> modules = sysModuleFeignService.getForTree(Optional.ofNullable(platformCode).orElse(aierUiProperties.getSiteCode()));
        return modules;
    }

    @RequiresPermissions("Module:view")
    @RequestMapping(value="/page", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody PageResponse<Map<String, Object>> page(Map<String, Object> map, Long parentId, ModuleCondition moduleCondition) {
        moduleCondition.getCondition().put("parent", parentId);
        PageResponse<Map<String, Object>> page = sysModuleFeignService.getPageByParent(moduleCondition);
        return page;
    }

    @RequiresPermissions("Module:view")
    @RequestMapping(value="/view/{id}", method={RequestMethod.GET, RequestMethod.POST})
    public String view(@PathVariable Long id, Map<String, Object> map) {
        SysModule module = sysModuleFeignService.getModulePermissionsById(id);
        map.put("module", module);
        return VIEW;
    }

    @RequiresPermissions("Module:delete")
    @PostMapping(value="/delete/{id}")
    public @ResponseBody Map<String,Object> delete(@PathVariable Long id) {
        sysModuleFeignService.delete(id);
        return success("删除模块成功！");
    }

    /**
     * 检查系统编码是否唯一
     */
    @RequestMapping(value = "/checkCode", method = RequestMethod.POST)
    public @ResponseBody boolean checkCode(String previousCode, String moduleCode) {
        if (StringUtils.isEmpty(moduleCode)) {
            return false;
        }
        if (StringUtils.isNotBlank(previousCode) && previousCode.equalsIgnoreCase(moduleCode)){
            return true;
        }
        return sysModuleFeignService.getCodeUnique(previousCode, moduleCode);
    }

    /**
     * 检查系统编码是否唯一
     */
    @RequestMapping(value = "/checkName", method = RequestMethod.POST)
    public @ResponseBody boolean checkName(String previousName, String moduleName) {
        if (StringUtils.isEmpty(moduleName)) {
            return false;
        }
        if (StringUtils.isNotBlank(previousName) && previousName.equalsIgnoreCase(moduleName)){
            return true;
        }
        return sysModuleFeignService.getNameUnique(previousName, moduleName);
    }

    @RequiresPermissions("Module:view")
    @RequestMapping(value = "/getHosps", method = RequestMethod.POST)
    public @ResponseBody List<Institution> getHosps() {
        List<Institution> institutions = instService.getListByInstType(InstEnum.HOSP);
        return institutions;
    }
}
