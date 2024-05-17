package com.aier.cloud.biz.aams.controller;
import com.aier.cloud.biz.aams.entity.SysPermission;
import com.aier.cloud.biz.aams.service.SysPermissionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.aier.cloud.basic.starter.service.controller.BaseController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作表（需要权限管理的页面对象如按钮，图片，文件等） 前端控制器
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
@Controller
@RequestMapping("/api/service/biz/aams/sysPermission")
public class SysPermissionController extends BaseController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @ApiOperation(value="获取系统所有操作", notes="")
    @PostMapping(value = "/getAllPermissions")
    public @ResponseBody List<SysPermission> getAllPermissions(@RequestParam(required=false, name="platformCode") String platformCode) {
        return sysPermissionService.selectAllList(platformCode);
    }

    @ApiOperation(value="根据用户和站点（机构）获取所有操作信息", notes="")
    @PostMapping(value = "/selectListByUserAndInst")
    public @ResponseBody List<SysPermission> selectListByUserAndInst(@RequestParam("staffId") Long staffId, @RequestParam("instId") Long instId, @RequestParam("platformCode") String platformCode) {
        return sysPermissionService.selectListByUserAndInst(staffId, instId, platformCode);
    }

    @ApiOperation(value="查看当前用户在某医院的所有访问权限", notes="查看当前用户在某医院的所有访问权限")
    @PostMapping(value = "/lookUpAuthorize")
    public List<Map<String, Object>> lookUpAuthorize(@RequestParam("staffId")Long staffId, @RequestParam("instId") Long instId, @RequestParam("platformCode") String platformCode){
        return sysPermissionService.lookUpAuthorize(staffId, instId, platformCode);
    }

}
