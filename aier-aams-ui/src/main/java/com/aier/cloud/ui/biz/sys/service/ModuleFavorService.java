package com.aier.cloud.ui.biz.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;


/**
 * 收藏夹
 *
 * @author xiaokek
 * @since 2018年5月24日 下午4:54:28
 */
@FeignClient(name="aier-service-system")
public interface ModuleFavorService {
    
    /**
     * 获取收藏
     * @param staffId
     * @return
     */
    @RequestMapping(value = "/api/sys/sysModuleFavorite/getFavorModuleId", method = RequestMethod.POST)
    Set<Long> getFavorModuleId(@RequestParam("staffId") Long staffId);
    
    /**
     * 创建
     * @param favor
     * @return
     */
    @RequestMapping(value = "/api/sys/sysModuleFavorite/create", method = RequestMethod.POST)
    void create(Map favor);
 
    /**
     * 删除
     * @param favor
     * @return
     */
    @RequestMapping(value = "/api/sys/sysModuleFavorite/delete", method = RequestMethod.POST)
    void delete(Map favor);
}
