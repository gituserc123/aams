package com.aier.cloud.ui.biz.sys.service;

import com.aier.cloud.basic.api.request.condition.based.*;
import com.aier.cloud.basic.api.request.domain.based.DcgCodeDict;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


/**
 * 参数服务
 * @author xiaokek
 * @since 2018年1月29日 上午10:11:40
 */
@FeignClient(name="aier-service-based")
public interface DictService {

	/**
	 * 查码表
	 * @param paraType
	 * @param filter 过滤条件
	 * @return
	 */
    @RequestMapping("/api/based/dict/getList")
    List<Map> getList(@RequestParam("paraType") String paraType, @RequestParam("filter") String filter);
    
    /**
	 * 查很多个码表
	 * @param paraType
	 * @return
	 */
    @RequestMapping("/api/based/dict/getMoreList")
    Map<String,List> getMoreList(@RequestParam("paraType[]") List<String> paraType);

	/**
	 * 查询单个码表
	 * @param type
	 * @param filter
	 * @return
	 */
	@RequestMapping(value="/api/based/dcgCodeDict/getCodeDictList", method=RequestMethod.POST)
	List<Map> getCodeDictList(@RequestParam(value="type")String type, @RequestParam(value="filter", defaultValue="")String filter);

	/**
	 * 查询码表数据
	 * @param condition
	 * @return
	 */
	@RequestMapping(value="/api/based/dcgCodeDict/getListDetail", method=RequestMethod.POST)
	List<DcgCodeDict> getListDetail(@RequestBody CodeDictCondition condition);
    
    /**
     * findPrincipalDiag  
     * (查询诊断字典-李超提供接口)  
     * @param cond
     * @return    参数说明  
     * PageResponse<Map<String,Object>>
     *
     */
	@RequestMapping(value = "/api/based/diagDesc/findPrincipalDiag", method=RequestMethod.POST)
	public List findPrincipalDiag(@RequestBody DcgDiagDescCondition cond);

	/**
	 * findPrincipalDiagPage
	 * (分页查询诊断字典-李超提供接口)
	 * @param cond
	 * @return	参数说明
	 * PageResponse<Map<String,Object>>
	 */
	@RequestMapping(value = "/api/based/diagDesc/findPrincipalDiagPage", method=RequestMethod.POST)
	public PageResponse<Map<String,Object>> findPrincipalDiagPage(@RequestBody DcgDiagDescCondition cond);

    /**
     * getDiagnosticPage
     * (分页查询诊断字典-李超提供接口)
     * @param cond
     * @return	参数说明
     * PageResponse<Map<String,Object>>
     */
    @RequestMapping(value = "/api/based/diag/getDiagnostic", method=RequestMethod.POST)
    public PageResponse<Map<String,Object>> getDiagnosticPage(@RequestBody DcgDiagCondition cond);

	/**
	 * findByHospId
	 * (精确查询诊断字典-李超提供接口)
	 * 主要用于验证首页诊断信息是否存在当前诊断字典库中
	 * @param cond
	 * @return    参数说明
	 * List
	 *
	 */
	@RequestMapping(value = "/api/based/diag/findByHospId", method=RequestMethod.POST)
	public List findPrincipalDiagNoDesc(@RequestBody DcgDiagCondition cond);
	
	/**
	 * findOperICD  
	 * (查询手术字典-梁淼提供接口)  
	 * @param cond
	 * @return    参数说明  
	 * PageResponse<Map<String,Object>>
	 *
	 */
	@RequestMapping(value = "/api/based/dcgOperation/findOperICD", method=RequestMethod.POST)
	public List findOperICD(@RequestBody DcgOperationCondition cond);

	/**
	 * findByHospIdPage
	 * (分页查询手术字典-梁淼提供接口)
	 * @param cond
	 * @return    参数说明
	 * PageResponse<Map<String,Object>>
	 *
	 */
	@RequestMapping(value = "/api/based/dcgOperation/findByHospIdPage", method=RequestMethod.POST)
	public PageResponse<Map<String,Object>> findOperICDPage(@RequestBody DcgOperationCondition cond);

	/**
	 * findByHospId
	 * (精确查询手术字典-李超提供接口)
	 * 主要用于验证首页手术信息是否存在当前手术字典库中
	 * @param cond
	 * @return    参数说明
	 * List
	 *
	 */
	@RequestMapping(value = "/api/based/dcgOperation/findByHospId", method=RequestMethod.POST)
	public List findOperICDExact(@RequestBody DcgOperationCondition cond);
    
	/**
	 * findInjuryPoisonByHospId
	 * (获取损伤中毒外部原因字典-李超提供接口)
	 * @param cond
	 * @return    参数说明
	 * PageResponse<Map<String,Object>>
	 *
	 */
	@RequestMapping(value = "/api/based/diagInjuryPoison/findByHospId", method=RequestMethod.POST)
	public List findInjuryPoisonByHospId(@RequestBody DcgDiagInjuryPoisonCondition cond);

	/**
	 * findInjuryPoisonByHospIdPage
	 * (分页获取损伤中毒外部原因字典-李超提供接口)
	 * @param cond
	 * @return    参数说明
	 * PageResponse<Map<String,Object>>
	 *
	 */
	@RequestMapping(value = "/api/based/diagInjuryPoison/findByHospIdPage", method=RequestMethod.POST)
	public PageResponse<Map<String,Object>> findInjuryPoisonByHospIdPage(@RequestBody DcgDiagInjuryPoisonCondition cond);
	
	/**
	 * findPathologyByHospId  
	 * (获取病理诊断字典-李超提供接口)  
	 * @param cond
	 * @return    参数说明  
	 * PageResponse<Map<String,Object>>
	 *
	 */
	@RequestMapping(value = "/api/based/diagPathology/findByHospId", method=RequestMethod.POST)
	public List findPathologyByHospId(@RequestBody DcgDiagPathologyCondition cond);

	/**
	 * findPathologyByHospIdPage
	 * (分页获取病理诊断字典-李超提供接口)
	 * @param cond
	 * @return    参数说明
	 * PageResponse<Map<String,Object>>
	 *
	 */
	@RequestMapping(value = "/api/based/diagPathology/findByHospIdPage", method=RequestMethod.POST)
	public PageResponse<Map<String,Object>> findPathologyByHospIdPage(@RequestBody DcgDiagPathologyCondition cond);
	
	/**
	 * findByParamsForPage  
	 * (获取晶体类型及型号对照关系-李超提供接口)  
	 * @param cond
	 * @return    参数说明  
	 * PageResponse<Map<String,Object>>
	 *
	 */
	@RequestMapping(value = "/api/based/dcgCrystalTypeBrand/findByParamsForPage", method=RequestMethod.POST)
	public PageResponse<Map<String, Object>> findCrystalTypeBrand(@RequestBody DcgCrystalTypeBrandCondition cond);

    /**
     * 获取重庆版省市区街道
     * @param d
     * @return
     */
    @RequestMapping(value="/api/based/dcgRegion/getList", method=RequestMethod.POST)
    PageResponse<Map> getRegionList(BasedCommonCondition d);
}
