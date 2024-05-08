package com.aier.cloud.biz.common.feign;

import com.aier.cloud.basic.api.request.condition.based.BasedCommonCondition;
import com.aier.cloud.basic.api.request.condition.based.CodeDictContrastCondition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


/**
 *基础标准服务
 * @author xiaokek
 * @since 2018年2月28日 下午2:52:45
 */
@FeignClient(name="aier-service-based")
public interface BasedFeignService {
        
    /**
     * 获取参数值
     * @param type
     * @param filter
     * @return
     */
    @RequestMapping(value="/api/based/dcgCodeDict/getCodeDictList", method=RequestMethod.POST)
    List<Map> getCodeDictList(@RequestParam(value="type")String type, @RequestParam(value="filter", defaultValue="")String filter);
    
    /**
     * getProvince  
     * 获取省份列表信息  
     * @param province 省份代码/省份名称/省份首拼码
     * @return    参数说明  
     * List<Map>
     */
    @RequestMapping(value="/api/based/dcgRegion/getProvince", method=RequestMethod.POST)
    List<Map> getProvince(@RequestParam(value="province", defaultValue="")String province);
    
    /**
     * getCity  
     * 根据省份ID获取市级列表信息 
     * @param province 省份ID
     * @param city 市级代码/市级名称/市级首拼码
     * @return    参数说明  
     * List<Map>
     */
    @RequestMapping(value="/api/based/dcgRegion/getCity", method=RequestMethod.POST)
    List<Map> getCity(@RequestParam(value="province") int province, @RequestParam(value="city", defaultValue="") String city);
    
    /**
     * getDistrict  
     * 根据市级ID获取区县列表信息
     * @param city 市级ID
     * @param district 区县代码/区县名称/区县首拼码
     * @return    参数说明  
     * List<Map>
     */
    @RequestMapping(value="/api/based/dcgRegion/getDistrict", method=RequestMethod.POST)
    List<Map> getDistrict(@RequestParam(value="city") int city, @RequestParam(value="district", defaultValue="") String district);
    
    /**
     * getAllRegion  
     * (获取所有行政区划)  
     * @return    参数说明  
     * List<Map>
     *
     */
    @RequestMapping(value="/api/based/dcgRegion/getAll", method=RequestMethod.POST)
    List<Map>  getAllRegion(@RequestBody BasedCommonCondition cond);

    /**
     * findByParams
     * 获取病案与国标的对照关系
     * @param cond
     * @return
     */
    @RequestMapping(value = "/api/based/codeDictContrast/findByParams", method = RequestMethod.POST)
    List<Map<String,Object>> getDictContrastCountryStd(@RequestBody CodeDictContrastCondition cond);
}
