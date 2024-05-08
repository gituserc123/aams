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

import com.aier.cloud.basic.api.request.condition.sys.SysSqlDictCondition;
import com.aier.cloud.basic.api.request.domain.sys.SysSqlDict;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import com.aier.cloud.ui.biz.sys.service.SqlDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Sql字典表
 * @author 爱尔眼科 Liuyi
 * @since 2018-06-27 14:45:09
 */
@Controller
@RequestMapping("/ui/sys/sqlDict")
public class SqlDictController extends BaseController {
	static final String P_VIEW = "SysSqlDict:view";
	static final String P_SAVE = "SysSqlDict:save";

	@Autowired
	private SqlDictService sqlDictService;

	@RequiresPermissions(P_VIEW)
	@RequestMapping("/index")
	public String index() {
		return "sys/sqlDict/index";
	}

	@RequiresPermissions(P_SAVE)
	@RequestMapping(value = "/saveSysSqlDict")
	public @ResponseBody
	Map<String, Object> saveSysSqlDict(@RequestBody SysSqlDict sysSqlDict) {
		return success(sqlDictService.saveSysSqlDict(sysSqlDict, ShiroUtils.getId()));
	}

	@RequiresPermissions(P_VIEW)
	@RequestMapping(value = "/selectSysSqlDictForPage")
	public @ResponseBody
    PageResponse<Map<String, Object>> getList(SysSqlDictCondition cond) {
		return sqlDictService.selectSysSqlDictForPage(cond);
	}
}