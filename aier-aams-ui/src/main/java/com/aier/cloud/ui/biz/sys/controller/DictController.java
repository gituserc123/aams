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

import com.aier.cloud.basic.api.request.condition.based.BasedCommonCondition;
import com.aier.cloud.basic.api.request.condition.based.CodeDictCondition;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.ui.biz.sys.service.DictService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * /ui/sys/dict/getForm
 * 系统参数表
 * @author 爱尔眼科
 * @since 2018-02-06 14:45:09
 */
@Controller
@RequestMapping("/ui/sys/dict")
public class DictController extends BaseController {
 
	@Autowired
	private DictService ds;
	
	@RequiresPermissions(GLOBAL_PERM)
	@RequestMapping(value = "/getListForComb")
	public @ResponseBody Object getListForComb(@RequestParam("tag") String tag) {
		return easyuiResult(ds.getList(tag,""));
	}
	
	@RequiresPermissions(GLOBAL_PERM)
	@RequestMapping(value = "/getList")
	public @ResponseBody Object getList(@RequestParam("paraType") String paraType) {
		return this.success(ds.getList(paraType,""));
	}
	
	@RequiresPermissions(GLOBAL_PERM)
	@RequestMapping(value = "/getMoreList")
	public @ResponseBody Object getMoreList(@RequestParam("paraType[]") List<String> paraTypes) {
		if(CollectionUtils.isNotEmpty(paraTypes)) {
			return this.success(ds.getMoreList(paraTypes));
		}
		return this.success(Collections.EMPTY_MAP);
	}

	@RequiresPermissions(GLOBAL_PERM)
	@RequestMapping(value = "/getListDetail")
	public @ResponseBody Object getListDetail(@RequestBody CodeDictCondition condition) {
		return this.success(ds.getListDetail(condition));
	}
	
	@RequestMapping(value = "/getForm")
	public String getForm() {
		return "sys/demo/form";
	}

	@RequiresPermissions(GLOBAL_PERM)
	@RequestMapping(value="/getRegionList", method= RequestMethod.POST)
	public PageResponse<Map> getRegionList(BasedCommonCondition d) {
		return ds.getRegionList(d);
	}
}