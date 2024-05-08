package com.aier.cloud.biz.aams.controller;
import com.aier.cloud.basic.api.domain.enums.RoleTypeEnum;
import com.aier.cloud.basic.api.request.condition.sys.RoleCondition;
import com.aier.cloud.basic.common.exception.BizAssert;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.biz.aams.dao.SecRoleMapper;
import com.aier.cloud.biz.aams.entity.SysRole;
import com.aier.cloud.biz.aams.service.SysRoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aier.cloud.basic.starter.service.controller.BaseController;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
@Controller
@RequestMapping("/api/service/biz/aams/sysRole")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService rs;

    @Autowired
    private SecRoleMapper roleMapper;


    @ApiOperation(value="根据id查询角色Tree实体")
    @ApiParam(name="id", value="角色信息的id", required=true)
    @PostMapping(value = "/getForTree")
    public @ResponseBody List getForTree(@RequestBody SysRole r) {
        add("pid", "t_crs_sys_role|id|role_name", "parentName");
        add("roleType", RoleTypeEnum.class,"roleTypeName");
        add("modifer", "t_crs_sys_staff|id|name");
        return rs.getForTree(r);
    }

    @ApiOperation(value="根据id查询权限Tree实体")
    @ApiParam(name="roleId", value="角色信息的id", required=true)
    @PostMapping(value = "/getPermByRoleId")
    public @ResponseBody List getPermByRoleId(@RequestParam(value="roleId") Long roleId,
                                              @RequestParam(value="isEdit", required=false, defaultValue="true") boolean isEdit, @RequestParam(value="platformCode") String platformCode) {
        return rs.getPermByRoleId(roleId, isEdit, platformCode);
    }

    @ApiOperation(value="更新角色对应的权限")
    @PostMapping(value = "/updateRolePerm")
    public @ResponseBody void updateRolePerm(@RequestBody RoleCondition rc) {
        rs.updateRolePerm(rc);
    }

    @ApiOperation(value="修改角色")
    @PostMapping(value = "/update")
    public @ResponseBody void update(@RequestBody SysRole role) {
        rs.updateRole(role);
    }

    @ApiOperation(value="根据id查询权限Tree实体")
    @ApiParam(name="roleId", value="角色信息的id", required=true)
    @PostMapping(value = "/delete")
    public @ResponseBody void delete(@RequestParam(value="roleId") Long roleId) {
        rs.deleteRole(roleId);
    }

    @ApiOperation(value="根据id查询权限实体")
    @ApiParam(name="id", value="角色信息的id", required=true)
    @PostMapping(value = "/getById")
    public @ResponseBody SysRole getById(@RequestParam(value="id") Long id) {
        add("parentId", "t_sys_role|id|role_name", "parentName");
        return rs.selectById(id);
    }

    @ApiOperation(value="根据登录账号id和登录机构id获取其对应的所有权限角色列表")
    @PostMapping(value = "/selectRolesByStaff")
    public @ResponseBody List<String> selectRolesByStaff(@RequestParam(value="staffId") Long staffId, @RequestParam(value="instId") Long instId) {
        BizAssert.notNull(staffId, BizException.ERROR, "登录用户id不能为空");
        BizAssert.notNull(instId, BizException.ERROR, "机构id不能为空");
        return roleMapper.selectRolesByStaff(staffId, instId);
    }


    /*@ApiOperation(value="根据id查询权限Tree实体")
    @ApiParam(name="roleId", value="角色信息的id", required=true)
    @PostMapping(value = "/getReportByRoleId")
    public @ResponseBody List getReportByRoleId(@RequestParam(value="roleId") Long roleId,
                                                @RequestParam(value="isEdit", required=false, defaultValue="true") boolean isEdit, @RequestParam(value="platformCode") String platformCode) {
        add("REPORTTYPE", ReportEnum.class,"REPORTTYPEName");
        return rs.getReportByRoleId(roleId, isEdit, platformCode);
    }

    @ApiOperation(value="更新角色对应的权限")
    @PostMapping(value = "/updateRoleReport")
    public @ResponseBody void updateRoleReport(@RequestBody RoleCondition rc) {
        rs.updateRoleReport(rc);
    }*/


}
