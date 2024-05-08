package com.aier.cloud.ui.biz.sys.service;

import com.aier.cloud.basic.api.request.condition.sys.ConfigCondition;
import com.aier.cloud.basic.common.util.AierConfigType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


/**
 * 参数服务
 * @author xiaokek
 * @since 2018年3月12日 下午4:40:39
 */
@FeignClient(name="aier-service-based")
public interface ConfigService {

	/**
	 * 获取参数值
	 * @param type
	 * @return
	 */
    @RequestMapping(value="/api/based/sysConfig/getValue", method=RequestMethod.POST)
    String getValue(AierConfigType type);
    
    /**
	 * 获取多个参数值
	 * @param types
	 * @return
	 */
    @RequestMapping(value="/api/based/sysConfig/getValues", method=RequestMethod.POST)
    Map<String, String> getValues(@RequestParam(value="code")List<AierConfigType> types);
    
	   
    /**
     * 获取参数编码,支持分页
     * @param c ConfigCondition中的key可依据confCode和confName进行模糊搜索
     * @return
     */
    @RequestMapping(value="/api/based/sysConfig/getForList", method=RequestMethod.POST)
    Object getForList(ConfigCondition c);

	/**
	 * 修改参数值
	 * @param platformCode
	 * @param confCode
	 * @param value
	 * @return
	 */
    @RequestMapping(value="/api/based/sysConfig/updateValue", method=RequestMethod.POST)
    Void updateValue(@RequestParam("platformCode") String platformCode, @RequestParam("confCode") String confCode,
			@RequestParam(value="value") String value);
    
    
}
