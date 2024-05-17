/*
c * Copyright © 2004-2018 Aier EYE Hospital Group.
 * 爱尔眼科医院集团 版权所有
 *
 * Licensed under the Aier EYE Hospital Group License;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.aierchina.com/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 package com.aier.cloud.ui.biz.sys.controller;

import com.aier.cloud.basic.api.domain.enums.InstEnum;
import com.aier.cloud.basic.api.request.condition.sys.InstCondition;
import com.aier.cloud.basic.api.request.condition.sys.StaffCondition;
import com.aier.cloud.basic.api.request.domain.sys.InstitutionDept;
import com.aier.cloud.basic.api.request.domain.sys.StaffInst;
import com.aier.cloud.basic.api.response.domain.sys.Institution;
import com.aier.cloud.basic.api.response.domain.sys.SysStaffHosp;
import com.aier.cloud.basic.common.exception.BizAssert;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.basic.common.util.HanyuPinyinUtils;
import com.aier.cloud.basic.starter.ui.shiro.ShiroDbRealm;
import com.aier.cloud.basic.starter.ui.shiro.ShiroDbRealm.HashPassword;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import com.aier.cloud.ui.biz.sys.service.InstitutionService;
import com.aier.cloud.ui.biz.sys.service.LoginCodeService;
import com.aier.cloud.basic.starter.ui.feign.StaffService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 医院组织机构
 * @author rain_deng
 * @since 2018-05-11 14:45:09
 */
@Controller
@RequestMapping("/ui/sys/inst/hospital")
public class InstHospitalController extends BaseController {
    
    private static final String LIST   = "sys/inst/hospital/list";
    private static final String CREATE = "sys/inst/hospital/create";
    private static final String EDIT = "sys/inst/hospital/update";
    private static final String STAFF_DEPT_EDIT   = "sys/inst/hospital/staffDeptEdit";
    private static final String STAFF_DEPT_SAVE   = "sys/inst/hospital/staffDeptCreate";
    private static final String STAFF_EDIT = "sys/inst/hospital/staffEdit";
    private static final String STAFF_CREATE = "sys/inst/hospital/staffCreate";
    
    private static final String PERMISSION_CODE_VIEW   = "InstHospital:view";
    private static final String PERMISSION_CODE_SAVE   = "InstHospital:save";
    private static final String PERMISSION_CODE_EDIT   = "InstHospital:edit";
    private static final String PERMISSION_CODE_DELETE = "InstHospital:delete";
    private static final String PERMISSION_CODE_STAFF_EDIT = "InstHospital:staffEdit";
    private static final String PERMISSION_CODE_STAFF_CREATE = "InstHospital:staffCreate";
    private static final String PERMISSION_CODE_STAFF_DEPT_EDIT = "InstHospital:staffDeptEdit";
    private static final String PERMISSION_CODE_STAFF_DEPT_SAVE = "InstHospital:staffDeptSave";
    private static final String PERMISSION_CODE_STAFF_DEPT_DELETE = "InstHospital:staffDeptDelete";
    private static final String PERMISSION_CODE_STAFF_DEPT_USINGSIGN = "InstHospital:staffDeptUsingSign";

    private static final String PERMISSION_AUTHORIZE_CODE_VIEW = "ActivityAuthorize:view";
    
 
	@Autowired
	private InstitutionService instService;
	
	@Autowired
    private StaffService staffService;
	
	@Autowired
	private LoginCodeService loginCodeService;
	
	@RequiresPermissions(PERMISSION_CODE_SAVE)
	@GetMapping(value = "/preCreateDept")
	public String preCreateDept(Long parentId, Map<String, Object> map) {
	    Institution institution = instService.getById(parentId);
	    BizAssert.notNull(institution, BizException.ERROR, "父机构不存在");
	    map.put("parentId", parentId);
	    map.put("deptName", institution.getName());
	    map.put("institution", institution);
		return CREATE;
	}
	
	@RequiresPermissions(PERMISSION_CODE_SAVE)
    @PostMapping(value = "/createDept")
    public @ResponseBody Map<String,Object> createDept(InstitutionDept institutionDept) {
        Institution institution = instService.getById(institutionDept.getInstitution().getParent().getId());
        BizAssert.notNull(institution, BizException.ERROR, "父机构不存在");
        Long creator = ShiroUtils.getId();
        institutionDept.getInstitution().setCreator(creator);
        institutionDept.getDeptDetail().setCreator(creator);
        institutionDept.getInstitution().setFirstSpell(HanyuPinyinUtils.getFirstLettersUp(institutionDept.getInstitution().getName()));
        
        String message = hasValid(institutionDept.getInstitution());
        if (StringUtils.isNotBlank(message)) {
           return fail(message);
        }
        
        boolean b = instService.createDept(institutionDept);
        return b ? success("新增科室成功") : fail("新增科室失败");
    }
	
	@RequiresPermissions(PERMISSION_CODE_EDIT)
    @GetMapping(value = "/preEditDept")
    public String preEditDept(Long id, Map<String, Object> map) {
        Institution institution = instService.getDeptDetailByInstId(id);
        BizAssert.notNull(institution, BizException.ERROR, "机构不存在");
        map.put("institution", institution);
        return EDIT;
    }
	
	/**
	 * 编辑部门
	 * @param institutionDept
	 * @return
	 */
	@RequiresPermissions(PERMISSION_CODE_EDIT)
    @PostMapping(value = "/editDept")
    public @ResponseBody Map<String,Object> editDept(InstitutionDept institutionDept) {
        Institution institution = instService.getById(institutionDept.getInstitution().getParent().getId());
        BizAssert.notNull(institution, BizException.ERROR, "父机构不存在");
        Long modifer = ShiroUtils.getId();
        
        institutionDept.getInstitution().setCreator(modifer);
        institutionDept.getInstitution().setModifer(modifer);
        institutionDept.getDeptDetail().setModifer(modifer);
        institutionDept.getDeptDetail().setCreator(modifer);
        institutionDept.getInstitution().setFirstSpell(HanyuPinyinUtils.getFirstLettersUp(institutionDept.getInstitution().getName()));
        
        String message = hasValid(institutionDept.getInstitution());
        if (StringUtils.isNotBlank(message)) {
           return fail(message);
        }
        
        boolean b = instService.updateDept(institutionDept);
        return b ? success("修改科室成功") : fail("修改科室失败");
    }
	
	
    @PostMapping(value = "/getStaffDeptForTree")
    @ResponseBody
    public List<Map<String,Object>> getStaffDeptForTree() {
        return instService.getStaffDeptForTree(ShiroUtils.getInstId());
    }
    
    @RequiresPermissions(PERMISSION_CODE_STAFF_EDIT)
    @GetMapping(value = "/preStaffHospEdit/{staffId}/{deptId}")
    public String preStaffEdit(@PathVariable Long staffId, @PathVariable Long deptId, Map<String, Object> map) {
        Long instId = ShiroUtils.getInstId();
        SysStaffHosp sysStaffHosp = staffService.getStaffHospByIdAndHosp(staffId, instId, deptId);
        sysStaffHosp.getStaff().setFirstSpell(HanyuPinyinUtils.getFirstLettersUp(sysStaffHosp.getStaff().getName()));
        List<Institution> deptList = instService.getDeptListByStaffCodeAndInst(sysStaffHosp.getStaff().getCode(), instId);
        map.put("staffHosp", sysStaffHosp);
        map.put("deptList", deptList);
        return STAFF_EDIT;
    }
    
    @RequiresPermissions(PERMISSION_CODE_STAFF_EDIT)
    @PostMapping(value = "/staffHospEdit")
    public @ResponseBody Map<String,Object> staffHospEdit(SysStaffHosp sysStaffHosp) {
        Long operator = ShiroUtils.getId();
        sysStaffHosp.setHospId(ShiroUtils.getInstId());
        sysStaffHosp.setCreator(operator);
        sysStaffHosp.setModifer(operator);
        sysStaffHosp.getStaff().setModifer(operator);
        
        boolean result = staffService.staffHospEdit(sysStaffHosp);
        return result ? success("人员详细信息编辑成功") : fail("人员详细信息编辑失败");
    }
    
    
    /**
     * 创建用户： 分为集团内创建用户和 集团外创建用户
     * 集团内 选择用户
     * @param staffId
     * @param map
     * @return
     */
    @RequiresPermissions(PERMISSION_CODE_STAFF_CREATE)
    @GetMapping(value = "/preStaffCreate/{deptId}")
    public String preStaffCreate(@PathVariable("deptId") Long deptId, Map<String, Object> map) {
        map.put("deptId", deptId);
        //map.put("loginCode", loginCodeService.generateLoginCode("sys.logincode", String.valueOf(ShiroUtils.getTenantId())));
        return STAFF_CREATE;
    }
    
    /**
     * 集团内提交他院人员坐诊表单
     * 1. 注意已经转入的人员，不能重复转入
     * 2. 人员id，源机构id，科室id，机构id（会话中获取） ， 如果转入的人员是没有注册ahis过的医院的，更新staff表的冻结字段为：非冻结，新增staff_inst记录，
     * 3. 集团内的默认无有效期（手动启停即可），集团外的需要设置有效期
     * @return
     */
    @RequiresPermissions(PERMISSION_CODE_STAFF_CREATE)
    @PostMapping(value = "/staffCreate")
    public @ResponseBody Map<String,Object> staffCreate(StaffInst staffInst) {
        
        staffInst.setInstitutionId(ShiroUtils.getInstId());
        staffInst.setModifer(ShiroUtils.getId());
        boolean result = instService.staffInstCreate(staffInst);
        return result ? success("邀请他院人员坐诊成功") : fail("邀请他院人员坐诊失败");
    }
    
    /**
     * 集团外手动创建用户
     * @param staffId
     * @param map
     * @return
     */
    @RequiresPermissions(PERMISSION_CODE_STAFF_CREATE)
    @PostMapping(value = "/staffHospCreate")
    public @ResponseBody Map<String,Object> staffHospCreate(SysStaffHosp sysStaffHosp) {
        Long operator = ShiroUtils.getId();
        Long instId = ShiroUtils.getInstId();
        BizAssert.notNull(sysStaffHosp);
        sysStaffHosp.setHospId(instId);
        sysStaffHosp.setModifer(operator);
        sysStaffHosp.setCreator(operator);
        sysStaffHosp.getStaff().setCode(loginCodeService.generateLoginCode("sys.logincode", String.valueOf(ShiroUtils.getTenantId())));
        sysStaffHosp.getStaff().setModifer(operator);
        sysStaffHosp.getStaffInst().setModifer(operator);
        sysStaffHosp.getStaffInst().setInstitutionId(instId);
        sysStaffHosp.getStaff().setFirstSpell(HanyuPinyinUtils.getFirstLettersUp(sysStaffHosp.getStaff().getName()));
        HashPassword hashPassword = ShiroDbRealm.encryptPassword(sysStaffHosp.getStaff().getPassword());
        sysStaffHosp.getStaff().setSalt(hashPassword.salt);
        sysStaffHosp.getStaff().setPassword(hashPassword.password);
        
        boolean result = staffService.staffHospCreate(sysStaffHosp);
        return result ? success("邀请他院人员坐诊成功") : fail("邀请他院人员坐诊失败");
    }
	
	@RequiresPermissions(PERMISSION_CODE_STAFF_DEPT_EDIT)
    @GetMapping(value = "/preStaffChangeDept")
    public String preChangeDept(String ids, Map<String, Object> map) {
	    map.put("ids", ids);
        return STAFF_DEPT_EDIT;
    }
	
	@RequiresPermissions(PERMISSION_CODE_STAFF_DEPT_EDIT)
	@PostMapping(value = "/staffChangeDept")
    public @ResponseBody Map<String,Object> staffChangeDept(StaffInst staffInst) {
        Institution institution = instService.getById(staffInst.getDeptId());
        BizAssert.notNull(institution, BizException.ERROR, "机构不存在");
        staffInst.setInstitutionId(ShiroUtils.getInstId());
        staffInst.setModifer(ShiroUtils.getId());
        Boolean result = instService.staffChangeDept(staffInst);
        return result ? success("人员转移科室成功") : fail("人员转移科室失败");
    }
	
	@RequiresPermissions(PERMISSION_CODE_STAFF_DEPT_SAVE)
    @GetMapping(value = "/preStaffAddDept")
    public String preStaffAddDept(String ids, Map<String, Object> map) {
        map.put("ids", ids);
        return STAFF_DEPT_SAVE;
    }
    
    @RequiresPermissions(PERMISSION_CODE_STAFF_DEPT_SAVE)
    @PostMapping(value = "/staffAddDept")
    public @ResponseBody Map<String,Object> staffAddDept(StaffInst staffInst) {
        Institution institution = instService.getById(staffInst.getDeptId());
        BizAssert.notNull(institution, BizException.ERROR, "机构不存在");
        staffInst.setInstitutionId(ShiroUtils.getInstId());
        staffInst.setModifer(ShiroUtils.getId());
        boolean result = instService.staffAddDept(staffInst);
        return result ? success("人员新增科室成功") : fail("人员新增科室失败");
    }
    
    @RequiresPermissions(PERMISSION_CODE_STAFF_DEPT_DELETE)
    @PostMapping(value = "/staffRemoveDept")
    public @ResponseBody Map<String,Object> staffRemoveDept(String ids) {
        boolean result = instService.staffRemoveDept(ids);
        return result ? success("人员移除部门成功") : fail("人员移除部门失败");
    }
	
	@RequiresPermissions(PERMISSION_CODE_DELETE)
	@PostMapping(value = "/removeDept")
	public @ResponseBody Map<String,Object> removeDept(Long id) {
	    boolean result = instService.removeDept(id);
        return result ? success("删除科室成功") : fail("删除科室失败");
    }
	
	@RequiresPermissions(PERMISSION_CODE_STAFF_DEPT_USINGSIGN)
    @PostMapping(value = "/changeStaffDeptUsingSign/{id}")
    public @ResponseBody Map<String,Object> changeStaffDeptUsingSign(@PathVariable("id") Long id) {
	    BizAssert.notNull(id, BizException.ERROR, "参数不合法");
	    StaffInst staffInst = new StaffInst();
	    staffInst.setId(id); staffInst.setModifer(ShiroUtils.getId());
        boolean result = staffService.changeStaffDeptUsingSign(staffInst);
        return result ? success("人员科室启停操作成功") : fail("人员科室启停操作失败");
    }
	
	@RequiresPermissions(PERMISSION_CODE_VIEW)
    @RequestMapping(value = "/list")
    public String list(Map<String, Object> map) {
        return LIST;
    }
	
	@RequiresPermissions(PERMISSION_AUTHORIZE_CODE_VIEW)
    @PostMapping(value = "/getForTree")
    @ResponseBody
    public Object getForTree(Long parentId) {
        return instService.getForTree(Objects.isNull(parentId) ? ShiroUtils.getInstId() : parentId);
    }
	
	@RequiresPermissions(PERMISSION_CODE_VIEW)
	@RequestMapping(value = "/getInstByParent")
	@ResponseBody
	public Object getInstByParent(Long instId) {
		return instService.getInstByParent(Objects.isNull(instId) ? ShiroUtils.getInstId() : instId);
	}
	
	@RequiresPermissions(PERMISSION_CODE_VIEW)
    @PostMapping(value = "/getTreeByParent")
    public @ResponseBody List<Map<String,Object>> getTreeByParent() {
        return instService.getTreeByParent(ShiroUtils.getInstId());
    }
	
	@RequiresPermissions(PERMISSION_CODE_VIEW)
    @PostMapping(value = "/getStaffByInstAndDept")
    public @ResponseBody Object getStaffByInstAndDept(StaffCondition staffCondition) {
	    staffCondition.setHospId(ShiroUtils.getInstId());
        return staffService.getStaffByInstAndDept(staffCondition);
    }
	
	
	@RequiresPermissions(PERMISSION_CODE_VIEW)
	@RequestMapping(value = "/getInstByConditionForSelect")
	@ResponseBody
	public Object getInstByConditionForSelect(InstCondition sc) {
		sc.setInstId(ShiroUtils.getInstId());
		sc.setInstType(InstEnum.MEDICAL.getEnumCode());
		return instService.getInstByConditionForSelect(sc);
	}
	
	
	@RequiresPermissions(PERMISSION_CODE_VIEW)
    @RequestMapping(value = "/getStaffByConditionForSuggest")
    @ResponseBody
    public Object getStaffByConditionForSuggest(StaffCondition staffCondition) {
	    staffCondition.setInstId(ShiroUtils.getInstId());
        return staffService.getStaffByConditionForSuggest(staffCondition);
    }
	
	@RequiresPermissions(PERMISSION_CODE_VIEW)
	@PostMapping(value = "/getAllStaffByCondition")
    @ResponseBody
    public Object getAllStaffByCondition(StaffCondition staffCondition) {
        return staffService.getAllStaffByCondition(staffCondition);
    }
	
	@RequiresPermissions(PERMISSION_CODE_VIEW)
    @GetMapping(value = "/getListByInstTypeAndInstId")
    @ResponseBody
    public List<Institution> getListByInstTypeAndInstId() {
        return instService.getListByInstTypeAndInstId(InstEnum.HOSP, ShiroUtils.getInstId());
    }
}