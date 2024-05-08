package com.aier.cloud.biz.common.feign;

import java.util.List;
import java.util.Map;

import com.aier.cloud.basic.api.response.domain.outp.OutpRegist;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="aier-service-medical")
public interface MedicalFeignService {

	/**
	 * selectByDeptId  
	 * 根据科室ID获取病区数据
	 * @param deptId
	 * @return    参数说明  
	 * List<Map>
	 *
	 */
	@RequestMapping(value = "/api/inpnurse/inpWardDept/selectByDeptId", method = RequestMethod.POST)
	List<Map> selectByDeptId(@RequestParam(value = "deptId", defaultValue = "") Long deptId);

	/**
	 * 从Ahis中获取所有病区对应科室 liuyi
	 * @return
	 */
	@RequestMapping(value = "/api/ahisForAmrs/getAllWardDept", method = RequestMethod.POST)
	List<Map> getAllWardDept();

	/**
	 * 根据患者ID查询最近一次接诊的挂号信息
	 * @param patientId
	 * @return
	 */
	@RequestMapping(value = "/api/outp/regist/getLastRegistedByPtientId", method = RequestMethod.POST)
	OutpRegist getLastRegistedByPtientId(@RequestParam(value = "patientId") Long patientId);
}
