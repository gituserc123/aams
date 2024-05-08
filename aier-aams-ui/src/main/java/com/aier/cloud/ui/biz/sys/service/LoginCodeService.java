package com.aier.cloud.ui.biz.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 获取临时登录账号
 *
 * @author rain_deng 
 * @since 2018年3月30日 上午10:11:32
 */
@FeignClient(name="aier-service-based")
public interface LoginCodeService {
	
	/**
	 * 获取临时登录账号
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/api/based/sn/generateLoginCode", method = RequestMethod.POST)
	String generateLoginCode(@RequestParam(value="type") String type, @RequestParam(value="ahisHosp") String ahisHosp);
	
	
}
