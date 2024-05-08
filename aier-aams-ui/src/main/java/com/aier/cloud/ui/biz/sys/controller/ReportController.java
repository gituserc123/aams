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

import com.aier.cloud.basic.api.request.condition.sys.SysCommonCondition;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.basic.web.shiro.ShiroUtils;
import com.aier.cloud.ui.biz.sys.service.ReportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 报表权限管理
 * @author xiaokek
 * @since 2018年5月14日 下午2:47:57
 */
@Controller("com.aier.cloud.ui.biz.sys.controller.ReportController")
@RequestMapping("/ui/sys/report")
public class ReportController extends BaseController {
	
	private static final String P_REPORT = "ReportPerm:view";
	private static final String P_PERM = "ReportPerm:perm";
	
	private static final String P_REPORT_GROUP = "Report:view";
	private static final String P_EDIT_GROUP = "Report:edit";
	
	private static final String P_MY_REPORT = "MyReport:view";
 
	@Autowired
	private ReportService s;
	
	
	
	@RequiresPermissions(P_MY_REPORT)
	@RequestMapping(value = "/viewMyReport")
	public Object viewMyReport(SysCommonCondition m) {
		m.setId(ShiroUtils.getId());
		ModelAndView mv = new ModelAndView();
		mv.setViewName("sys/report/viewMyReport");
		mv.addObject("reports", s.getStaffReport(m));
		return mv;
	}
	
	@RequiresPermissions(P_MY_REPORT)
	@RequestMapping(value = "/getMyReport")
	public @ResponseBody Object getMyReport(SysCommonCondition m) {
		m.setRows(100);
		m.setId(ShiroUtils.getId());
		m.setStaffId(ShiroUtils.getId());
		return s.getStaffReport(m);
	}
	
	@RequiresPermissions(P_PERM)
	@RequestMapping(value = "/viewReportPerm")
	public Object viewReportPerm(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("sys/report/viewReportPerm");
		mv.addObject("id", id);
		return mv;
	}
	
	@RequiresPermissions(P_PERM)
	@RequestMapping(value = "/updateReportPerm")
	public @ResponseBody Object updateReportPerm(@RequestBody SysCommonCondition m) {
		s.updateReportPerm(m);
		return this.success();
	}
	
	@RequiresPermissions(P_PERM)
	@RequestMapping(value = "/getReportStaff")
	public @ResponseBody Object getReportStaff(SysCommonCondition m) {
		m.setDetailId(ShiroUtils.getInstId());
		return s.getReportStaff(m);
	}
	
	@RequiresPermissions(P_PERM)
	@RequestMapping(value = "/viewUpdatePerm")
	public Object viewUpdatePerm(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("sys/report/viewUpdatePerm");
		mv.addObject("id", id);
		return mv;
	}
	
	@RequiresPermissions(P_PERM)
	@RequestMapping(value = "/updatePerm")
	public @ResponseBody Object updatePerm(@RequestBody SysCommonCondition m) {
		s.updatePerm(m);
		return this.success();
	}
	
	@RequiresPermissions(P_REPORT)
	@RequestMapping(value = "/getStaffReport")
	public @ResponseBody Object getStaffReport(SysCommonCondition m) {
		m.setStaffId(ShiroUtils.getId());
		return s.getStaffReport(m);
	}

	@RequiresPermissions(P_REPORT)
	@RequestMapping(value = "/viewGetPerm")
	public Object viewGetPerm(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("sys/report/viewGetPerm");
		mv.addObject("id", id);
		return mv;
	}
	
    @RequiresPermissions(P_REPORT)
    @GetMapping(value="/reportPermList")
    public ModelAndView reportPermList() {
    	ModelAndView mv = new ModelAndView();
		mv.setViewName("sys/report/reportPermList");
		mv.addObject(ShiroUtils.getStaff());
		return mv;
    }
    
    @RequiresPermissions(P_REPORT_GROUP)
    @GetMapping(value="/reportList")
    public ModelAndView reportList() {
    	ModelAndView mv = new ModelAndView();
		mv.setViewName("sys/report/reportList");
		mv.addObject(ShiroUtils.getStaff());
		return mv;
    }
    
    @RequiresPermissions(P_REPORT_GROUP)
	@RequestMapping(value = "/getList")
	public @ResponseBody Object getList(SysCommonCondition m) {
		return s.getList(m);
	}
    
    @RequiresPermissions(P_EDIT_GROUP)
	@RequestMapping(value = "/updateReport")
	public @ResponseBody Object updateReport(@RequestBody SysCommonCondition m) {
    	m.setStaffId(ShiroUtils.getId());
		s.updateReport(m);
		return this.success();
	}
}