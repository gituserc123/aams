package com.aier.cloud.ui.biz.sys.service;

import com.aier.cloud.basic.api.request.condition.sys.SysSqlDictCondition;
import com.aier.cloud.basic.api.request.domain.sys.SysSqlDict;
import com.aier.cloud.basic.api.response.domain.base.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


/**
 * 用户服务
 * @author xiaokek
 * @since 2018年1月29日 上午10:11:40
 */
@FeignClient(name="aier-service-system")
public interface SqlDictService {
    /**
     * saveSysSqlDict
     * @param sysSqlDict
     * @return
     */
    @RequestMapping(value = "/api/sys/sysSqlDict/saveSysSqlDict", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean saveSysSqlDict(@RequestBody SysSqlDict sysSqlDict, @RequestParam("loginUserId") Long loginUserId);
    
    /**
     * sql分页
     * @param cond
     * @return
     */
    @RequestMapping(value = "/api/sys/sysSqlDict/selectSysSqlDictForPage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<Map<String, Object>> selectSysSqlDictForPage(@RequestBody SysSqlDictCondition cond);
}
