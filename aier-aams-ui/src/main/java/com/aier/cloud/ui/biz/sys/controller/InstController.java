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
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.center.common.context.UserContext;
import com.aier.cloud.ui.biz.sys.service.InstitutionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 医院组织机构
 * @author xiaokek
 * @since 2018-02-06 14:45:09
 */
@Controller
@RequestMapping("/ui/sys/inst")
public class InstController extends BaseController {
 
	@Autowired
	private InstitutionService instService;
	
	@RequiresPermissions("Inst:view")
	@RequestMapping(value = "/centerAndUsers")
	public String centerAndUsers() {
		return "sys/inst/centerAndUsers";
	}
	
	@RequiresPermissions("Inst:edit")
	@RequestMapping(value = "/addHosp")
	public String addHosp() {
		return "sys/inst/addHosp";
	}

	@RequiresPermissions("Inst:view")
	@RequestMapping(value = "/getForTree")
	@ResponseBody
	public Object getForTree(@RequestParam(value="id",defaultValue="100001") Long id) {
		return this.instService.getForTree(id);
	}

	@RequiresPermissions("Inst:view")
	@RequestMapping(value = "/getInstByParent")
	@ResponseBody
	public Object getInstByParent(@RequestParam(value="instId",defaultValue="100001") Long instId) {
		return this.instService.getInstByParent(instId);
	}

	@RequiresPermissions(GLOBAL_PERM)
	@RequestMapping(value = "/getInstByConditionForSelect")
	@ResponseBody
	public Object getInstByConditionForSelect(InstCondition sc, @RequestParam(value="deptIdss[]", required=false) List<Long> deptIds) {
		if(CollectionUtils.isNotEmpty(deptIds)) {
			sc.setDeptIds(deptIds);
		}
		sc.setInstId(UserContext.getInstId());
		sc.setInstType(InstEnum.MEDICAL.getEnumCode());
		return instService.getInstByConditionForSelect(sc);
	}
}