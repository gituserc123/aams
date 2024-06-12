package com.aier.cloud.biz.aams.controller;

import com.aier.cloud.aams.api.request.condition.AuthorizeAamsCondition;
import com.aier.cloud.basic.api.domain.constant.CommSymbol;
import com.aier.cloud.basic.api.domain.enums.RoleTypeEnum;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.basic.common.exception.BizAssert;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.basic.starter.service.controller.BaseController;
import com.aier.cloud.biz.aams.entity.SysRole;
import com.aier.cloud.biz.aams.entity.SysStaffInstRole;
import com.aier.cloud.biz.aams.service.SysRoleService;
import com.aier.cloud.biz.aams.service.SysStaffInstRoleService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 用户授权管理
 * @author rain_deng
 */
@RestController
@RequestMapping("/api/sys/authorize")
public class AuthorizeController  extends BaseController {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysStaffInstRoleService staffInstRoleService;


    @ApiOperation(value="获取所有角色树", notes="获取所有角色树")
    @PostMapping(value = "/getRoleForTree")
    public List<Map<String,Object>> getRoleForTree(@RequestParam("platformCode") String platformCode) {
        EntityWrapper<SysRole> ew = new EntityWrapper<>();
        ew.setSqlSelect("id \"id\", parent as \"pid\", role_name as \"name\" ");
        ew.eq("platform_code", roleService.getTruePlatformCode(platformCode)).orderBy("orders asc");
        List<Map<String, Object>> list = roleService.selectMaps(ew);
        return list;
    }

    @ApiOperation(value="获取医院角色树", notes="获取医院角色树")
    @PostMapping(value = "/getHospRoleForTree")
    public List<Map<String,Object>> getHospRoleForTree(@RequestParam("platformCode") String platformCode) {
        EntityWrapper<SysRole> ew = new EntityWrapper<>();
        ew.setSqlSelect("id \"id\", parent as \"pid\", role_name as \"name\" ");
        ew.eq("role_type", RoleTypeEnum.HOSP.getRoleType()).eq("platform_code",  roleService.getTruePlatformCode(platformCode));
        ew.orderBy("orders asc");
        List<Map<String, Object>> list = roleService.selectMaps(ew);
        return list;
    }

    @ApiOperation(value="获取用户所在机构的所有角色树", notes="获取所有角色树")
    @PostMapping(value = "/getRoleTreeByStaffInst")
    public List<Map<String,Object>> getRoleTreeByStaffInst(@RequestParam("staffId") Long staffId, @RequestParam("instId") Long instId, @RequestParam("platformCode") String platformCode) {
        BizAssert.notNull(staffId, BizException.ERROR, "用户 staffId 不能为空");
        BizAssert.notNull(instId, BizException.ERROR, "机构 instId 不能为空");
        return roleService.selectRoleTreeByStaffInst(staffId, instId, platformCode);
    }


    @ApiOperation(value="获取用户所在医院的所有角色树", notes="获取所有角色树")
    @PostMapping(value = "/getRoleTreeByStaffInstHosp")
    public List<Map<String,Object>> getRoleTreeByStaffInstHosp(@RequestParam("staffId") Long staffId, @RequestParam("instId") Long instId, @RequestParam("platformCode") String platformCode) {
        BizAssert.notNull(staffId, BizException.ERROR, "用户 staffId 不能为空");
        BizAssert.notNull(instId, BizException.ERROR, "机构 instId 不能为空");
        return roleService.selectRoleTreeByStaffInstHosp(staffId, instId, RoleTypeEnum.HOSP.getRoleType(), platformCode);
    }

    @ApiOperation(value="获取角色详细信息", notes="获取角色详细信息")
    @PostMapping(value = "/getRoleById")
    public SysRole getRoleById(@RequestParam("roleId") Long roleId) {
        return roleService.selectById(roleId);
    }

    @ApiOperation(value="获取当前角色下，某医院的所有用户", notes="获取所有角色树")
    @PostMapping(value = "/getListByRoleAuthorize")
    public PageResponse<Map<String,Object>> getListByRoleAuthorize(@RequestBody AuthorizeAamsCondition authorizeCondition){
        Page<Map<String,Object>> page = tranToPage(authorizeCondition);
        add("institutionId", "t_sys_institution|id|name", "instName");
        add("staffId", "t_sys_staff|id|name", "staffName");
        // add("roleid", "sj.T_SYS_ROLE|ID|role_NAME", "roleName1");
        /*EntityWrapper<SysStaffInstRole> wrapper = new EntityWrapper<>();
        wrapper.eq("role_id", authorizeCondition.getRoleId());
        if(authorizeCondition.getInstitution() != null && authorizeCondition.getInstitution().intValue() > 0){
            wrapper.eq("institution_id", authorizeCondition.getInstitution());
        }
        if(Objects.nonNull(authorizeCondition.getStaffIds()) && authorizeCondition.getStaffIds().size()>0){
            wrapper.in("staff_id",authorizeCondition.getStaffIds());
        }
        Page<Map<String, Object>> pages = staffInstRoleService.selectMapsPage(page,wrapper);
        return returnPageResponse(pages);*/
        List<Map<String, Object>> retLists = staffInstRoleService.getStaffRoleInstByCond(page,authorizeCondition);
        return  returnPageResponse(page,retLists);
    }

    @ApiOperation(value="获取还未授权当前医院当前角色的用户", notes="获取还未授权当前医院当前角色的用户")
    @PostMapping(value = "/getListNotAuthorize")
    public List<Map<String,Object>> getListNotAuthorize(@RequestBody AuthorizeAamsCondition authorizeCondition){
        // 暂未实现
        return Lists.newArrayList();
    }



    @PostMapping(value = "/updateByRole")
    public Boolean update(@RequestParam("staffIds") String staffIds,
                          @RequestParam("instId") Long instId,
                          @RequestParam(name="roleId", defaultValue = "") Long roleId) {
        BizAssert.notEmpty(staffIds,  BizException.ERROR, "用户 staffIds 不能为空");
        BizAssert.notNull(roleId, BizException.ERROR, "角色 roleId 不能为空");
        BizAssert.notNull(instId,   BizException.ERROR, "机构 instId 不能为空");

        // 先将staffIds中，根据instId，roleId查询出来的重复staffId去掉
        List<SysStaffInstRole> staffInstRoles = staffInstRoleService.getStaffRoleInstList(roleId,instId,null);
        List<Long> existStaffIds = staffInstRoles.stream().map(SysStaffInstRole::getStaffId).collect(Collectors.toList());
        List<Long> paramStaffIds = Arrays.asList(staffIds.split(CommSymbol.SEPARATOR_COMMA)).stream().map(Long::parseLong).collect(Collectors.toList());
        Set<Long> existSetStaffIds = new HashSet<>(existStaffIds);
        paramStaffIds.removeIf(existSetStaffIds::contains);

        return staffInstRoleService.batchUpdateStaffInstRoleByRole(paramStaffIds,instId,roleId);
    }

    @PostMapping(value = "/updateByStaff")
    Boolean update(@RequestParam(name="staffId", defaultValue = "") Long staffId,
                   @RequestParam("instId") Long instId,
                   @RequestParam(name="roleIds") String roleIds){
        BizAssert.notNull(staffId,  BizException.ERROR, "用户 staffId 不能为空");
        //BizAssert.notEmpty(roleIds, BizException.ERROR, "角色 roleIds 不能为空");
        BizAssert.notNull(instId,   BizException.ERROR, "机构 instId 不能为空");

        // 先将roleIds中，根据instId，staffId查询出来的重复roleId去掉
        /*List<SysStaffInstRole> staffInstRoles = staffInstRoleService.getStaffRoleInstList(null,instId,staffId);
        List<Long> existRoleIds = staffInstRoles.stream().map(SysStaffInstRole::getRoleId).collect(Collectors.toList());
        List<Long> paramRoleIds = Arrays.asList(roleIds.split(CommSymbol.SEPARATOR_COMMA)).stream().map(Long::parseLong).collect(Collectors.toList());
        Set<Long> existSetRoleIds = new HashSet<>(existRoleIds);
        paramRoleIds.removeIf(existSetRoleIds::contains);*/
        List<Long> paramRoleIds = Arrays.asList(roleIds.split(CommSymbol.SEPARATOR_COMMA)).stream().map(Long::parseLong).collect(Collectors.toList());
        staffInstRoleService.batchDeleteStaffInstRoleByStaff(staffId,instId,paramRoleIds);
        return staffInstRoleService.batchUpdateStaffInstRoleByStaff(staffId,instId, paramRoleIds);
    }

    @PostMapping(value = "/delete")
    public Boolean delete(@RequestParam("id") Long id){
        SysStaffInstRole staffInstRole = staffInstRoleService.selectById(id);
        BizAssert.notNull(staffInstRole, BizException.ERROR, "授权id不存在");
        return staffInstRoleService.deleteById(id);
    }

}
