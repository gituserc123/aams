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

import com.aier.cloud.basic.api.request.condition.sys.StaffCondition;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.basic.api.response.domain.sys.Staff;
import com.aier.cloud.basic.common.exception.BizAssert;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.basic.starter.ui.shiro.ShiroDbRealm;
import com.aier.cloud.basic.starter.ui.shiro.ShiroDbRealm.HashPassword;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import com.aier.cloud.ui.biz.sys.service.StaffService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Staff
 * 用户管理
 * @author 爱尔眼科
 * @since 2018-02-06 14:45:09
 */
@Controller
@RequestMapping("/ui/sys/staff")
public class StaffController extends BaseController {
 
	@Autowired
	private StaffService staffService;
	
	private static final String LIST = "sys/staff/list";
	
	private static final String HOSPITAL_LIST = "sys/staff/hospital/list";
    
    private static final String PERMISSION_CODE_VIEW = "SysStaff:view";
    private static final String PERMISSION_CODE_RESET_PWD = "SysStaff:resetPwd";
    private static final String PERMISSION_CODE_LOCK_STATUS = "SysStaff:lockStatus";
    private static final String PERMISSION_CODE_FREEZE_STATUS = "SysStaff:freezeStatus";

	@RequiresPermissions(GLOBAL_PERM)
	@RequestMapping(value = "/getStaffByCondition")
	@ResponseBody
	public Object getInstByParent(StaffCondition sc) {
		return staffService.getStaffByCondition(sc);
	}
	
	@RequiresPermissions(GLOBAL_PERM)
	@RequestMapping(value = "/getHospStaff")
    public @ResponseBody Object getHospStaff(@RequestBody StaffCondition sc) {
		sc.setInstId(ShiroUtils.getInstId());
    	List o =  staffService.getHospStaff(sc);
		return this.easyuiResult(o); 
    }
	
	@RequiresPermissions(PERMISSION_CODE_VIEW)
    @GetMapping(value = "/list")
    public String getList() {
        return LIST; 
    }
	
	@RequiresPermissions("SysStaffHosp:view")
    @GetMapping(value = "/hospital/list")
    public String getHospList() {
        return HOSPITAL_LIST; 
    }
	
	@RequiresPermissions(PERMISSION_CODE_VIEW)
    @PostMapping(value = "/page")
    public @ResponseBody
    PageResponse<Staff> getPage(StaffCondition sc) {
	    PageResponse<Staff> page =  staffService.getPage(sc);
        return page; 
    }
	
	@RequiresPermissions("SysStaffHosp:view")
    @PostMapping(value = "/hospital/page")
    public @ResponseBody
    PageResponse<Staff> getHospPage(StaffCondition sc) {
        sc.setInstId(ShiroUtils.getInstId());
        PageResponse<Staff> page =  staffService.getPageByInst(sc);
        return page; 
    }
	
	@RequiresPermissions("SysStaffHosp:resetPwd")
    @PostMapping(value = "/hospResetPwd")
    public @ResponseBody Map<String, Object> hospResetPwd(String ids) {
        List<Staff> list = staffService.getListByIds(ids);
        BizAssert.notEmpty(list, BizException.ERROR, "所选择的用户列表不存在！");
        for (Staff staff: list) {
            HashPassword hashPassword = ShiroDbRealm.encryptPassword(ShiroDbRealm.DEFAULT_PASSWORD);
            staff.setSalt(hashPassword.salt);
            staff.setPassword(hashPassword.password);
            staff.setChangePassword(false);
        }
        staffService.resetBatchStaff(list);
        
        // 发邮件通知
        return success("重置密码成功，默认密码为："+ ShiroDbRealm.DEFAULT_PASSWORD);
    }
    
    @RequiresPermissions("SysStaffHosp:lockStatus")
    @PostMapping(value = "/hospLockStatus")
    public @ResponseBody Map<String, Object> hospLockStatus(String ids) {
        List<Staff> list = staffService.getListByIds(ids);
        BizAssert.notEmpty(list, BizException.ERROR, "所选择的用户列表不存在！");
        for (Staff staff: list) {
            staff.setLocked(!staff.getLocked());
            if (!staff.getLocked()) {
                staff.setLoginFailureCount(0);
            }else{
                staff.setLockedDate(new Date());
            }
        }
        staffService.resetBatchStaff(list);
        return success("设置未锁定/已锁定状态成功！");
    }
	
	@RequiresPermissions(PERMISSION_CODE_RESET_PWD)
    @PostMapping(value = "/resetPwd")
    public @ResponseBody Map<String, Object> resetPwd(String ids) {
	    List<Staff> list = staffService.getListByIds(ids);
	    BizAssert.notEmpty(list, BizException.ERROR, "所选择的用户列表不存在！");
	    for (Staff staff: list) {
            HashPassword hashPassword = ShiroDbRealm.encryptPassword(ShiroDbRealm.DEFAULT_PASSWORD);
            staff.setSalt(hashPassword.salt);
            staff.setPassword(hashPassword.password);
            staff.setChangePassword(false);
        }
	    staffService.resetBatchStaff(list);
	    return success("重置密码成功，默认密码为："+ ShiroDbRealm.DEFAULT_PASSWORD);
    }
	
	@RequiresPermissions(PERMISSION_CODE_LOCK_STATUS)
    @PostMapping(value = "/lockStatus")
    public @ResponseBody Map<String, Object> lockStatus(String ids) {
        List<Staff> list = staffService.getListByIds(ids);
        BizAssert.notEmpty(list, BizException.ERROR, "所选择的用户列表不存在！");
        for (Staff staff: list) {
            staff.setLocked(!staff.getLocked());
            if (!staff.getLocked()) {
                staff.setLoginFailureCount(0);
            }else{
                staff.setLockedDate(new Date());
            }
        }
        staffService.resetBatchStaff(list);
        return success("设置未锁定/已锁定状态成功！");
    }
	
	@RequiresPermissions(PERMISSION_CODE_FREEZE_STATUS)
    @PostMapping(value = "/freezeStatus")
    public @ResponseBody Map<String, Object> freezeStatus(String ids) {
        List<Staff> list = staffService.getListByIds(ids);
        BizAssert.notEmpty(list, BizException.ERROR, "所选择的用户列表不存在！");
        for (Staff staff: list) {
            staff.setStatus(!staff.getStatus());
        }
        staffService.resetBatchStaff(list);
        return success("设置未冻结/已冻结状态成功！");
    }
    
}