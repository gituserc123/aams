package com.aier.cloud.ui.biz.aams.controller;

import com.aier.cloud.aams.api.request.domain.SysRole;
import com.aier.cloud.basic.api.request.condition.sys.RoleCondition;
import com.aier.cloud.basic.common.properties.AierUiProperties;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import com.aier.cloud.ui.biz.aams.feign.SysRoleFeignService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ui/sys/role")
public class SysRoleUiController extends BaseController {

    @Autowired
    private SysRoleFeignService rs;
    @Autowired
    private AierUiProperties au;

    @RequiresPermissions("Role:view")
    @RequestMapping(value = "/roleManage")
    public String roleManage() {
        return "sys/role/roleManage";
    }

    @RequiresPermissions("Role:view")
    @RequestMapping(value = "/pop/roleCreate")
    public String roleCreate() {
        return "sys/role/pop/roleCreate";
    }

    @RequiresPermissions("Role:view")
    @RequestMapping(value = "/openPerm")
    public ModelAndView openPerm(@RequestParam Map param) {
        return new ModelAndView("sys/role/openPerm", param);
    }

    @RequiresPermissions("Role:view")
    @RequestMapping(value = "/reportRole")
    public ModelAndView reportRole(@RequestParam Map param) {
        return new ModelAndView("sys/role/reportRole", param);
    }
    @RequiresPermissions("Role:view")
    @RequestMapping(value = "/getReportByRoleId")
    public @ResponseBody Object getReportByRoleId(@RequestParam(value="roleId", required=false) Long roleId,
                                                  @RequestParam(value="isEdit", required=false, defaultValue="true") boolean isEdit) {
        if(roleId == null) {
            return Collections.EMPTY_LIST;
        }
        //return rs.getReportByRoleId(roleId, isEdit, au.getSiteCode());
        return new Object();
    }

    @RequiresPermissions("Role:edit")
    @RequestMapping(value = "/updateRoleReport")
    public @ResponseBody Object updateRoleReport(@RequestParam("roleId") Long roleId,
                                                 @RequestParam(value="permIds[]", required=false ) List<Long> permIds) {
        RoleCondition rc = new RoleCondition();
        rc.setRoleId(roleId);
        rc.setPermIds(permIds);
        rc.setPlatformCode(au.getSiteCode());
        // rs.updateRoleReport(rc);
        return this.success("修改成功！");
    }

    @RequiresPermissions("Role:view")
    @RequestMapping(value = "/getPermByRoleId")
    public @ResponseBody Object getPermByRoleId(@RequestParam(value="roleId", required=false) Long roleId,
                                                @RequestParam(value="isEdit", required=false, defaultValue="true") boolean isEdit) {
        if(roleId == null) {
            return Collections.EMPTY_LIST;
        }
        return rs.getPermByRoleId(roleId, isEdit, au.getSiteCode());
    }

    @RequiresPermissions("Role:view")
    @RequestMapping(value = "/getForTree")
    public @ResponseBody Object getForTree(SysRole r) {
        r.setPlatformCode(au.getSiteCode());
        return this.easyuiResult(rs.getForTree(r));
    }

    @RequiresPermissions("Role:view")
    @RequestMapping(value = "/getById")
    public @ResponseBody Object getById(@RequestParam("id")Long id) {
        return this.success(rs.getById(id));
    }

    @RequiresPermissions("Role:edit")
    @RequestMapping(value = "/updateRolePerm")
    public @ResponseBody Object updateRolePerm(@RequestParam("roleId") Long roleId,
                                               @RequestParam(value="permIds[]", required=false ) List<Long> permIds) {
        RoleCondition rc = new RoleCondition();
        rc.setRoleId(roleId);
        rc.setPermIds(permIds);
        rc.setPlatformCode(au.getSiteCode());
        rs.updateRolePerm(rc);
        return this.success("修改成功！");
    }

    @RequiresPermissions("Role:edit")
    @RequestMapping(value = "/update")
    public @ResponseBody Object update(@RequestBody SysRole role) {
        role.setModifer(ShiroUtils.getId());
        role.setPlatformCode(au.getSiteCode());
        rs.update(role);
        return this.success("保存成功！");
    }

    @RequiresPermissions("Role:delete")
    @RequestMapping(value = "/delete")
    public @ResponseBody Object delete(@RequestParam("roleId") Long roleId) {
        rs.delete(roleId);
        return this.success();
    }

}
