package com.aier.cloud.ui.biz.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;


/**
 * 报表权限
 * @author xiaokek
 * @since 2018年5月24日 下午4:54:28
 */
@FeignClient(name="aier-service-system")
public interface PlatformService {

    @RequestMapping(value = "/api/sys/platform/list", method = RequestMethod.POST)
	List<Map> list();

}
