package com.aier.cloud.ui.biz.sys.service;

import com.aier.cloud.basic.api.request.condition.sys.GroupProblemCondition;
import com.aier.cloud.basic.api.request.condition.sys.SysCommonCondition;
import com.aier.cloud.basic.api.request.domain.sys.SysHospProblem;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 用户服务
 * 
 * @author xiaokek
 * @since 2018年1月29日 上午10:11:40
 */
@FeignClient(name = "aier-service-system")
public interface HospProblemService {
	/**
	 * 获取角色值List
	 * 
	 * @param r
	 * @return
	 */
	@RequestMapping(value = "/api/sys/hospProblem/getForHospList", method = RequestMethod.POST)
	Object getForHospList(SysCommonCondition r);

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/api/sys/hospProblem/saveOrUpdate", method = RequestMethod.POST)
	void saveOrUpdate(Map m);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/sys/hospProblem/delete", method = RequestMethod.POST)
	void delete(@RequestParam(value = "id") Long id);

	/**
	 * getById
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/sys/hospProblem/getById", method = RequestMethod.POST)
	Object getById(@RequestParam(value = "id") Long id);

	/**
	 * 集团查询问题列表
	 * @param r
	 * @return
	 */
	@RequestMapping(value = "/api/sys/hospProblem/getForGroupList", method = RequestMethod.POST)
    PageResponse<Map<String, Object>> getForGroupList(GroupProblemCondition r);

	/**
	 * 集团查看问题详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/sys/hospProblem/getDetailById", method = RequestMethod.POST)
	Map<String, Object> getDetailById(@RequestParam("id") Long id);

	/**
	 * 回复问题
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "/api/sys/hospProblem/reply", method = RequestMethod.POST)
	boolean reply(SysHospProblem m);
}
