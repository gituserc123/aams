/*
 * Copyright © 2004-2018 Aier EYE Hospital Group.
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

import com.aier.cloud.basic.api.request.condition.sys.RoleCondition;
import com.aier.cloud.basic.api.response.domain.sys.Role;
import com.aier.cloud.basic.common.properties.AierUiProperties;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import com.aier.cloud.ui.biz.sys.service.RoleService;
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

/**
 * 角色管理
 * @author xiaokek
 * @since 2018-03-12 16:28:03
 */
@Controller
@RequestMapping("/ui/sys/role/common")
public class RoleController extends BaseController {
 
	@Autowired
	private RoleService rs;
	
	@Autowired
    private AierUiProperties aierUiProperties;
	
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
	@RequestMapping(value = "/getPermByRoleId")
	public @ResponseBody Object getPermByRoleId(@RequestParam(value="roleId", required=false) Long roleId,
			@RequestParam(value="isEdit", required=false, defaultValue="true") boolean isEdit) {
		if(roleId == null) {
			return Collections.EMPTY_LIST;
		}
		return rs.getPermByRoleId(roleId, isEdit, aierUiProperties.getSiteCode());
	}
	
	@RequiresPermissions("Role:view")
	@RequestMapping(value = "/getForTree")
	public @ResponseBody Object getForTree(Role r) {
	    r.setPlatformCode(aierUiProperties.getSiteCode());
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
		rc.setPlatformCode(aierUiProperties.getSiteCode());
		rs.updateRolePerm(rc);
		return this.success("修改成功！");
	}
	
	@RequiresPermissions("Role:edit")
	@RequestMapping(value = "/update")
	public @ResponseBody Object update(@RequestBody Role role) {
		role.setModifer(ShiroUtils.getId());
		role.setPlatformCode(aierUiProperties.getSiteCode());
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