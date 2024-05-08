package com.aier.cloud.biz.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 获取流水号
 *
 * @author rain_deng 
 * @since 2018年3月30日 上午10:11:32
 */
@FeignClient(name="aier-service-based")
public interface DchSnService {
	
	/**
	 * 获取流水号
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/api/based/sn/generate", method = RequestMethod.POST)
	String generate(@RequestParam(value="type") String type);

	/**
	 * 单据号生成根据租户id做前缀
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/api/based/sn/generateByHosp", method = RequestMethod.POST)
	String generateByHosp(@RequestParam(value="type") String type);
}
