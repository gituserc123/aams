package com.aier.cloud.biz.aams.controller;

import com.aier.cloud.aams.api.request.condition.AuthorizeAamsCondition;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;


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
        add("INSTITUTIONID", "t_sys_institution|ID|NAME", "instName");
        add("STAFFID", "t_sys_staff|ID|NAME", "staffName");
        add("ROLEID", "db:t_sys_role|ID|role_NAME", "roleName1");
        EntityWrapper<SysStaffInstRole> wrapper = new EntityWrapper<>();
        wrapper.eq("role_id", authorizeCondition.getRoleId());
        if(authorizeCondition.getInstitution() != null && authorizeCondition.getInstitution().intValue() > 0){
            wrapper.eq("institution_id", authorizeCondition.getInstitution());
        }
        if(Objects.nonNull(authorizeCondition.getStaffIds()) && authorizeCondition.getStaffIds().size()>0){
            wrapper.in("staff_id",authorizeCondition.getStaffIds());
        }
        Page<Map<String, Object>> pages = staffInstRoleService.selectMapsPage(page,wrapper);
        return returnPageResponse(pages);
    }

    @ApiOperation(value="获取还未授权当前医院当前角色的用户", notes="获取还未授权当前医院当前角色的用户")
    @PostMapping(value = "/getListNotAuthorize")
    public List<Map<String,Object>> getListNotAuthorize(@RequestBody AuthorizeAamsCondition authorizeCondition){
        // 暂未实现
        return Lists.newArrayList();
    }

}
