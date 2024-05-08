package com.aier.cloud.biz.common.feign;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aier.cloud.basic.api.response.domain.sys.Staff;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.aier.cloud.basic.api.request.condition.sys.AutoCompleteCondition;
import com.aier.cloud.basic.api.request.condition.sys.InstCondition;
import com.aier.cloud.basic.api.request.condition.sys.StaffCondition;
import com.aier.cloud.basic.api.request.domain.sys.SysParam;
import com.aier.cloud.biz.common.entity.SysSqlDict;


/**
 *系统服务
 * @author xiaokek
 * @since 2018年2月28日 下午2:52:45
 */
@FeignClient(name="aier-service-system")
public interface SysFeignService {
	
	/**
	  * 根据ids查询包含id：name的键值对，主要用于翻译
     * @param ids
     * @return
     */
    @RequestMapping("/api/sys/inst/getNamesByIds")
    Map<Long,String> getInstNamesByIds(@RequestBody Set<Long> ids);
    
    /**
     * 根据ids查询包含id：name的键值对，主要用于翻译
     * @param ids
     * @return
     */
    @RequestMapping("/api/sys/staff/getNamesByIds")
    Map<Long,String> getStaffNamesByIds(@RequestBody Set<Long> ids);

    /**
     * 根据ids查询包含id：name的键值对，主要用于翻译
     * @param ids
     * @return
     */
    @RequestMapping(value = "/api/sys/staff/getById", method = RequestMethod.POST)
    Staff getStaffById(@RequestParam("id") Long id);
    
	/**
	 * 联想组件查询
	 * @param autoCompleteCondition
	 * @return
	 */
    @RequestMapping(value = "/api/aier-service-system/autoComplete/query", method = RequestMethod.POST)
    List<Map<String,Object>> getAutoCompleteService(@RequestBody AutoCompleteCondition autoCompleteCondition);

    /**
	 * 联想组件查询
	 * @param sql
	 * @return
	 */
    @RequestMapping(value = "/api/aier-service-system/autoComplete/queryBySql", method = RequestMethod.POST)
    List<Map<String,Object>> queryBySql(@RequestParam("sql") String sql);
    /**
	 * 联想组件查询
	 * @param sql
	 * @return
	 */
    @RequestMapping(value = "/api/aier-service-system/autoComplete/getLazyDictValue", method = RequestMethod.POST)
    List<Map> getLazyDictValue(@RequestParam(value="type") String type,
    		@RequestParam(value="param") String param);
    

    /**
     *  selectSysSqlDictByCode
     * @param code
     * @return
     */
    @RequestMapping(value = "/api/sys/sysSqlDict/selectSysSqlDictByCode", method = RequestMethod.POST)
    SysSqlDict selectSysSqlDictByCode(@RequestParam("code") String code);

    /**
     * 获取运行参数
     * @param platCode
     * @param paramCode
     * @return
     */
    @RequestMapping(value = "/api/sys/sysParam/getSysParam")
    SysParam getSysParam(@RequestParam("platCode") String platCode, @RequestParam("paramCode") String paramCode);
    

    /**
     * 根据医院id取医院名称
     * @param hospIds
     * @return
     */
    @RequestMapping(value = "/api/sys/inst/getNamesByAhisHosp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Map<Long, String> getNamesByAhisHosp(Set<Long> hospIds);
    
    /**
     * getHospStaff  
     * 	获取医院职工
     * @param sc
     * @return    参数说明  
     * List<Map>
     *
     */
    @RequestMapping(value = "/api/sys/staff/getHospStaff", method = RequestMethod.POST)
    List<Map<String,Object>> getHospStaff(@RequestBody StaffCondition sc);
    
    /**
     * getInstByConditionForSelect  
     * 	获取医院科室
     * @param sc
     * @return    参数说明  
     * List<Map>
     *
     */
    @RequestMapping(value = "/api/sys/inst/getInstByConditionForSelect", method = RequestMethod.POST)
    List<Map<String,Object>> getInstByConditionForSelect(@RequestBody InstCondition sc);
}
