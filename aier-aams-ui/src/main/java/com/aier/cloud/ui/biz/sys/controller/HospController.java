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

import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.ui.biz.sys.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 医院组织机构
 * @author xiaokek
 * @since 2018-02-06 14:45:09
 */
@Controller
@RequestMapping("/ui/sys/hosp")
public class HospController extends BaseController {
 
	@Autowired
	private InstitutionService instService;
	
	@RequestMapping(value = "/hosAndUsers")
	public String hosAndUsers() {
		return "sys/hosp/hosAndUsers";
	}
	
	@RequestMapping(value = "/getForTree")
	@ResponseBody
	public Object getForTree(@RequestParam(value="id",defaultValue="12") Long id) {
		return this.instService.getForTree(id);
	}
	
	@RequestMapping(value = "/getInstByParent")
	@ResponseBody
	public Object getInstByParent(@RequestParam(value="instId",defaultValue="1") Long instId) {
		return this.instService.getInstByParent(instId);
	}
}