package com.aier.cloud.biz.aams.controller;
import com.aier.cloud.basic.api.request.condition.sys.ModuleCondition;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.biz.aams.dao.SysModuleMapper;
import com.aier.cloud.biz.aams.entity.SysModule;
import com.aier.cloud.biz.aams.entity.SysPermission;
import com.aier.cloud.biz.aams.service.SysModuleService;
import com.aier.cloud.biz.aams.service.SysPermissionService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aier.cloud.basic.starter.service.controller.BaseController;

import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源模块表 前端控制器
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
@Controller
@RequestMapping("/api/service/biz/aams/sysModule")
public class SysModuleController extends BaseController {


    @Autowired
    private SysModuleService sysModuleService;

    @Autowired
    private SysModuleMapper sysModuleMapper;

    @Autowired
    private SysPermissionService sysPermissionService;

    @ApiOperation(value="获取除根节点以外的所有菜单", notes="")
    @PostMapping(value = "/getModules")
    public @ResponseBody List<SysModule> getModules() {
        return sysModuleService.selectList(new EntityWrapper<SysModule>().where("module_code<>{0}", SysModule.MODULE_ROOT_CODE));
    }

    @ApiOperation(value="门户普通用户获取所有菜单", notes="")
    @PostMapping(value = "/getListByPortal")
    public @ResponseBody List<SysModule> getListByPortal(@RequestParam("staffId") Long staffId, @RequestParam("instId") Long instId) {
        List<SysModule> moduleInsts = sysModuleMapper.selectList(new EntityWrapper<SysModule>().isNotNull("insts"));
        List<SysModule> modules = sysModuleMapper.getListByPortal(staffId, instId);
        if (CollectionUtils.isNotEmpty(modules)) {
            List<SysModule> moduleInst = Lists.newArrayList();
            for (SysModule module : moduleInsts) {
                // 某些设置了机构菜单权限的菜单
                modules.forEach(i -> {
                    // 如果当前菜单 和 权限菜单一致，且 菜单机构和当前机构匹配 
                    if(i.getId().equals(module.getId()) && !module.getInsts().contains(instId.toString())) {
                        moduleInst.add(i);
                    }
                });
            }
            return modules.stream().filter(item -> !moduleInst.contains(item)).collect(Collectors.toList());
        } else {
            return modules;
        }
    }

    @ApiOperation(value="门户获取所有菜单", notes="")
    @PostMapping(value = "/getAllListByPortal")
    public @ResponseBody List<SysModule> getAllListByPortal() {
        return sysModuleMapper.getAllListByPortal();
    }


    @ApiOperation(value="获取除根节点以外的所有菜单", notes="")
    @PostMapping(value = "/getModulesByPlatform")
    public @ResponseBody List<SysModule> getModulesByPlatform(@RequestParam("platformCode") String platformCode) {
        return sysModuleService.selectList(new EntityWrapper<SysModule>().ne("module_code", SysModule.MODULE_ROOT_CODE).eq("platform_code", platformCode));
    }

    @ApiOperation(value="获取根节点", notes="获取根节点")
    @PostMapping(value = "/getRoot")
    public @ResponseBody SysModule getRoot(@RequestParam("platformCode") String platformCode) {
        return sysModuleService.selectOne(new EntityWrapper<SysModule>().eq("module_code", SysModule.MODULE_ROOT_CODE).eq("platform_code", platformCode));
    }

    @ApiOperation(value="根据当前父根节点获取以下的子节点菜单", notes="")
    @PostMapping(value = "/getPageByParent")
    public @ResponseBody PageResponse<Map<String, Object>> getPageByParent(@RequestBody ModuleCondition moduleCondition) {
        Page<Map<String, Object>> page = tranToPage(moduleCondition);
        add("PARENTID", "t_sys_module|id|module_name", "PARENTNAME");
        EntityWrapper<SysModule> wrapper = new EntityWrapper<SysModule>();
        wrapper.like(moduleCondition.getSearchType(), moduleCondition.getSearchValue());
        if (StringUtils.isNotBlank(moduleCondition.getPlatformCode())) {
            wrapper.eq("platform_code", moduleCondition.getPlatformCode());
        }
        wrapper.orderBy("orders");
        Page<Map<String, Object>> pages = sysModuleService.selectMapsPage(page,wrapper);
        return returnPageResponse(pages);
    }

    @ApiOperation(value="根据根节点id获取其下面的所有节点", notes="")
    @PostMapping(value = "/getForTree")
    public @ResponseBody List<Map<String, Object>> getForTree(@RequestParam("platformCode") String platformCode) {
        EntityWrapper<SysModule> ew = new EntityWrapper<SysModule>();
        ew.setSqlSelect("id \"id\", parent as \"pid\", module_name as \"name\", treepath as \"treepath\" ");
        ew.eq("platform_code", platformCode).orderBy("orders asc");
        List<Map<String, Object>> list = sysModuleService.selectMaps(ew);
        return list;
    }

    @ApiOperation(value="查询某平台所有菜单列表", notes="系统首页加载菜单列表, 无序的")
    @PostMapping(value = "/getAllList")
    public @ResponseBody List<SysModule> getAllList(@RequestParam("platformCode") String platformCode) {
        return sysModuleService.getAllList(platformCode);
    }

    @ApiOperation(value="根据根节点id获取其下面的所有节点", notes="系统首页加载菜单列表")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "Long")
    @PostMapping(value = "/getMenuForTree")
    public @ResponseBody List<SysModule> getMenuForTree(@RequestParam("id") Long id) {
        return sysModuleService.getMenuForTree(id);
    }

    @ApiOperation(value="根据id获取菜单信息", notes="")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "Long")
    @PostMapping(value = "/getById")
    public @ResponseBody SysModule getById(@RequestParam("id") Long id) {
        return sysModuleService.selectById(id);
    }

    @ApiOperation(value="根据机构id 模糊匹配菜单id集合", notes="")
    @ApiImplicitParam(name = "instId", value = "机构id", required = true, dataType = "Long")
    @PostMapping(value = "/getIdListByInstId")
    public @ResponseBody List<Long> getIdListByInstId(@RequestParam("instId") Long instId) {
        return sysModuleMapper.getListByInst(instId);
    }

    @ApiOperation(value="根据id获取菜单和操作信息", notes="")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "Long")
    @PostMapping(value = "/getModulePermissionsById")
    public @ResponseBody SysModule getModulePermissionsById(@RequestParam("id") Long id) {
        SysModule module = sysModuleService.getById(id);
        if (!ObjectUtils.isEmpty(module)) {
            module.setPermissions(sysPermissionService.selectList(new EntityWrapper<SysPermission>().eq("module_id", id)));
        }
        return module;
    }

    @ApiOperation(value="创建菜单信息", notes="")
    @ApiImplicitParam(name = "module", value = "菜单信息", required = true, dataType = "com.aier.cloud.service.system.entity.Module")
    @PostMapping(value = "/create")
    public @ResponseBody Boolean create(@RequestBody SysModule module) {
        return sysModuleService.insert(module);
    }


    @ApiOperation(value="编辑菜单信息", notes="")
    @ApiImplicitParam(name = "module", value = "菜单信息", required = true, dataType = "com.aier.cloud.service.system.entity.Module")
    @PostMapping(value = "/update")
    public @ResponseBody Boolean update(@RequestBody SysModule module) {
        return sysModuleService.updateById(module);
    }

    @ApiOperation(value="删除菜单信息", notes="")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "Long")
    @PostMapping(value = "/delete")
    public @ResponseBody Boolean delete(@RequestParam("id") Long id) {
        return sysModuleService.deleteById(id);
    }

    @ApiOperation(value="检查模块代码唯一性", notes="")
    @PostMapping(value = "/getCodeUnique")
    public @ResponseBody Boolean getCodeUnique(@RequestParam("previousCode") String previousCode, @RequestParam("currentCode") String currentCode) {
        if (StringUtils.isNotBlank(previousCode) && previousCode.equalsIgnoreCase(currentCode)){
            return true;
        }
        int count = sysModuleService.selectCount(new EntityWrapper<SysModule>().where("lower(module_code) = {0}", currentCode.toLowerCase()));
        return !(count>0);
    }

    @ApiOperation(value="检查模块名称唯一性", notes="")
    @PostMapping(value = "/getNameUnique")
    public @ResponseBody Boolean getNameUnique(@RequestParam("previousName") String previousName, @RequestParam("currentName") String currentName) {
        if (StringUtils.isNotBlank(previousName) && previousName.equalsIgnoreCase(currentName)){
            return true;
        }
        int count = sysModuleService.selectCount(new EntityWrapper<SysModule>().eq("module_name", currentName)
                .eq("grade", 3));
        return !(count>0);
    }

}
