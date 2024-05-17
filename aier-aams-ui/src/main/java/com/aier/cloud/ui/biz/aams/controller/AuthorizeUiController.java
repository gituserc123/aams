package com.aier.cloud.ui.biz.aams.controller;

import cn.hutool.json.JSONUtil;
import com.aier.cloud.aams.api.request.condition.AuthorizeAamsCondition;
import com.aier.cloud.aams.api.request.domain.SysRole;
import com.aier.cloud.basic.api.request.condition.sys.StaffCondition;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.basic.common.properties.AierUiProperties;
import com.aier.cloud.ui.biz.aams.feign.SysPermissionFeignService;
import com.aier.cloud.ui.biz.aams.feign.SysRoleFeignService;
import com.aier.cloud.basic.starter.ui.feign.StaffService;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.ui.biz.aams.feign.AuthorizeFeignService;
import com.aier.cloud.ui.biz.sys.service.InstitutionService;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ui/sys/authorize")
public class AuthorizeUiController   extends BaseController {

    @Autowired
    private AuthorizeFeignService authorizeService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private AierUiProperties au;

    @Autowired
    private SysPermissionFeignService permissionFeignService;

    @Autowired
    private SysRoleFeignService roleFeignService;

    private static final String LIST = "sys/authorize/list";
    private static final String CREATE = "sys/authorize/create";
    private static final String UPDATE = "sys/authorize/update";
    private static final String VIEW = "sys/authorize/view";

    private static final String PERMISSION_CODE_VIEW = "Authorize:view";
    private static final String PERMISSION_CODE_SAVE = "Authorize:view";
    private static final String PERMISSION_CODE_UPDATE = "Authorize:view";
    private static final String PERMISSION_CODE_DELETE = "Authorize:view";


    @Autowired
    private StaffService staffService;


    @RequestMapping(value = "/getStaffTest", method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object getStaffTest() {
        StaffCondition sc = new StaffCondition();
        sc.setInstId(100002L);
        sc.setStaffKey("陈");
        sc.setRows(500);
        Object obj = staffService.getAllStaffByCondition(sc);

        return obj;
    }



    @RequiresPermissions(PERMISSION_CODE_VIEW)
    @GetMapping(value="/list")
    public String list(Map<String, Object> map) {
        return LIST;
    }

    @RequiresPermissions(PERMISSION_CODE_VIEW)
    @PostMapping(value = "/getRoleForTree")
    public @ResponseBody List<Map<String,Object>> getRoleForTree() {
        return authorizeService.getRoleForTree("aams");
    }

    @RequiresPermissions(PERMISSION_CODE_VIEW)
    @PostMapping(value = "/getRoleTreeByStaffInst/{staffId}/{instId}")
    public @ResponseBody List<Map<String,Object>> getRoleTreeByStaffInst(@PathVariable Long staffId, @PathVariable Long instId) {
        return authorizeService.getRoleTreeByStaffInst(staffId, instId, au.getSiteCode());
    }

    @RequiresPermissions(PERMISSION_CODE_VIEW)
    @PostMapping(value = "/getTreeByParent")
    public @ResponseBody List<Map<String,Object>> getTreeByParent(Long parentId) {
        return institutionService.getTreeByParent(parentId);
    }

    @RequiresPermissions(PERMISSION_CODE_VIEW)
    @PostMapping(value = "/search")
    public @ResponseBody PageResponse<Map<String,Object>> search(AuthorizeAamsCondition authorizeCondition) {
        //authorizeCondition.setInstitution(ShiroUtils.getInstId());
        //authorizeCondition.setPlatformCode(au.getSiteCode());
        // 构造查询参数
        if(Objects.nonNull(authorizeCondition.getKeyword()) && (!authorizeCondition.getKeyword().equals(""))){
            StaffCondition sc = new StaffCondition();
            sc.setInstId(authorizeCondition.getInstitution());
            sc.setStaffKey(authorizeCondition.getKeyword());
            sc.setRows(10000);
            Object obj = staffService.getAllStaffByCondition(sc);
            if(Objects.nonNull(obj)){
                JSONObject jsonObject = (JSONObject) obj;
                if(Objects.nonNull(jsonObject) && jsonObject.getJSONArray("rows").size() >0){
                    List<Long> staffIds = jsonObject.getJSONArray("rows").stream().map(jo -> JSONUtil.parseObj(jo).getLong("ID")).collect(Collectors.toList());
                    authorizeCondition.setStaffIds(staffIds);
                }
            }
        }
        return authorizeService.getListByRoleAuthorize(authorizeCondition);
    }

    @RequiresPermissions(PERMISSION_CODE_VIEW)
    @PostMapping(value = "/searchStaffAuthorize")
    public @ResponseBody PageResponse<Map<String,Object>> getListByStaffAuthorize(AuthorizeAamsCondition authorizeCondition) {
        // 暂未实现
        return null;
    }

    @RequiresPermissions(PERMISSION_CODE_VIEW)
    @PostMapping(value = "/getStaffListNotAuthorize")
    public @ResponseBody List<Map<String,Object>> getStaffListNotAuthorize(AuthorizeAamsCondition authorizeCondition) {
        // 暂未实现
        return null;
    }

    @RequiresPermissions(PERMISSION_CODE_VIEW)
    @GetMapping(value="/lookUpAuth/{staffId}/{instId}")
    public String viewAuth(Map<String, Object> map, @PathVariable Long staffId, @PathVariable Long instId) {
        map.put("staffId", staffId);
        map.put("instId", instId);
        return VIEW;
    }

    @RequiresPermissions(PERMISSION_CODE_VIEW)
    @PostMapping(value="/lookUpAuth/{staffId}/{instId}")
    public  @ResponseBody List<Map<String,Object>> lookUpAuth(Map<String, Object> map, @PathVariable Long staffId, @PathVariable Long instId) {
        return permissionFeignService.lookUpAuthorize(staffId, instId,au.getSiteCode());
    }

    @RequiresPermissions(PERMISSION_CODE_SAVE)
    @GetMapping(value="/create/{roleId}")
    public String preCreate(Map<String, Object> map, @PathVariable Long roleId) {
        SysRole role = roleFeignService.getById(roleId);
        map.put("role", role);
        return CREATE;
    }



}
