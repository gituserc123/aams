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

import com.aier.cloud.basic.api.request.condition.sys.GroupProblemCondition;
import com.aier.cloud.basic.api.request.condition.sys.SysCommonCondition;
import com.aier.cloud.basic.api.request.domain.sys.SysHospProblem;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import com.aier.cloud.ui.biz.sys.service.HospProblemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 医院问题反馈
 *
 * @author xiaokek
 * @since 2018年8月7日 上午9:28:47
 */
@Controller
@RequestMapping("/ui/sys/hospProblem")
public class HospProblemController extends BaseController {
	static final String P_VIEW = GLOBAL_PERM;
	static final String P_SAVE = GLOBAL_PERM;
	static final String PG_VIEW = "GroupProblem:view";
	static final String PG_REPLY = "GroupProblem:reply";

	@Autowired
	private HospProblemService hospProblemService;

	@RequiresPermissions(P_VIEW)
	@RequestMapping("/feedbackListHosp")
	public String index() {
		return "sys/hospProblem/feedbackListHosp";
	}

	@RequiresPermissions(P_VIEW)
	@RequestMapping(value = "/getById")
	public @ResponseBody Object getById(@RequestParam(value = "id") Long id) {
		return success(hospProblemService.getById(id));
	}

	@RequiresPermissions(P_VIEW)
	@RequestMapping(value = "/getForHospList")
	public @ResponseBody Object getForHospList(SysCommonCondition cond) {
		cond.setId(ShiroUtils.getId());
		return hospProblemService.getForHospList(cond);
	}

	/**
	 * 详情页
	 * 
	 * @return
	 */
	@RequiresPermissions(P_VIEW)
	@RequestMapping("/feedbackDetailsHosp")
	public ModelAndView feedbackDetailsHosp(@RequestParam(value = "id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("sys/hospProblem/feedbackDetailsHosp");
		mv.addObject("obj", hospProblemService.getDetailById(id));
		return mv;
	}

	@RequiresPermissions(P_SAVE)
	@RequestMapping(value = "/saveOrUpdate")
	public @ResponseBody Map<String, Object> saveOrUpdate(@RequestParam Map m) {
		if(!m.containsKey("id")){
			m.put("creator", ShiroUtils.getId());
			m.put("hospId", ShiroUtils.getTenantId());
			m.put("hospName", ShiroUtils.getInstName());
			m.put("declarerName", ShiroUtils.getLoginName());
		}
		hospProblemService.saveOrUpdate(m);
		return success();
	}

	@RequiresPermissions(P_SAVE)
	@RequestMapping(value = "/delete")
	public @ResponseBody Object delete(@RequestParam(value = "id") Long id) {
		hospProblemService.delete(id);
		return success();
	}

	/** 集团功能 */

	/**
	 * 集团功能主页
	 * 
	 * @return
	 */
	@RequiresPermissions(PG_VIEW)
	@RequestMapping("/feedbackList")
	public String feedbackList() {
		return "sys/hospProblem/feedbackList";
	}

	/**
	 * 详情页
	 * 
	 * @return
	 */
	@RequiresPermissions(PG_VIEW)
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request, Long id) {
		Object obj = hospProblemService.getDetailById(id);
		request.setAttribute("obj", obj);
		return "sys/hospProblem/feedbackDetails";
	}

	/**
	 * 查询问题列表
	 * 
	 * @return
	 */
	@RequiresPermissions(PG_VIEW)
	@RequestMapping("/getForGroupList")
	@ResponseBody
	public Object getForGroupList(GroupProblemCondition cond) {
		return hospProblemService.getForGroupList(cond);
	}

	/**
	 * 回复问题
	 * 
	 * @return
	 */
	@RequiresPermissions(PG_REPLY)
	@RequestMapping("/reply")
	@ResponseBody
	public Object reply(SysHospProblem sysHospProblem) {
		sysHospProblem.setModifer(ShiroUtils.getId());
		hospProblemService.reply(sysHospProblem);
		return success();
	}
}