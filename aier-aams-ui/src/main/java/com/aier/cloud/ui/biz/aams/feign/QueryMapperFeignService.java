package com.aier.cloud.ui.biz.aams.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "aier-aams-service")
public interface QueryMapperFeignService {

    @RequestMapping(value = "/api/service/biz/aams/query/queryCount")
    @ResponseBody
    Integer queryCount(@RequestParam("queryFeild") String queryFeild, @RequestParam("table") String table, @RequestParam("cond") String cond);

    @RequestMapping(value = "/api/service/biz/aams/query/queryList")
    @ResponseBody
    List<Map<String, Object>> queryList(@RequestParam("queryFeild") String queryFeild, @RequestParam("table") String table, @RequestParam("cond") String cond);

    @RequestMapping(value = "/api/service/biz/aams/query/commonQueryList", method = RequestMethod.POST)
    @ResponseBody
    List<Map<String, Object>> commonQueryList(@RequestParam("sql") String sql, @RequestParam("params") Object... params);

    @RequestMapping(value = "/api/service/biz/aams/query/commonQueryListBean", method = RequestMethod.POST)
    @ResponseBody
    <T> List<T> commonQueryListBean(@RequestParam("sql") String sql,
                                           @RequestParam("entityName") String entityName,
                                           @RequestParam("params") Object... params) ;
    @RequestMapping(value = "/api/service/biz/aams/query/deleteById")
    @ResponseBody
    Boolean deleteById(@RequestParam("entityName") String entityName, @RequestParam("id") Long id);

    @RequestMapping(value = "/api/service/biz/aams/query/deleteByMap")
    @ResponseBody
    Boolean deleteByMap(@RequestParam("entityName") String entityName,@RequestBody Map<String, Object> conditions);

    @RequestMapping(value = "/api/service/biz/aams/query/insertOrUpdate")
    @ResponseBody Map<String, Object> insertOrUpdate(@RequestBody Map<String, Object> mData,@RequestParam("entityName") String entityName) throws Exception;



    }
