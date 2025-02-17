package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.basic.api.response.domain.base.PageResponse;
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

    @RequestMapping(value = "/api/service/biz/aams/query/commonQueryListBeanByMap", method = RequestMethod.POST)
    @ResponseBody
    <T> List<T> commonQueryListBeanByMap(@RequestParam("sql") String sql,
                                           @RequestParam("entityName") String entityName,
                                           @RequestBody Map<String, ?> paramMap);

    @RequestMapping(value = "/api/service/biz/aams/query/deleteById")
    @ResponseBody
    Boolean deleteById(@RequestParam("entityName") String entityName, @RequestParam("id") Long id);

    @RequestMapping(value = "/api/service/biz/aams/query/deleteByMap")
    @ResponseBody
    Boolean deleteByMap(@RequestParam("entityName") String entityName,@RequestBody Map<String, Object> conditions);

    @RequestMapping(value = "/api/service/biz/aams/query/insertOrUpdate")
    @ResponseBody Map<String, Object> insertOrUpdate(@RequestBody Map<String, Object> mData,@RequestParam("entityName") String entityName) throws Exception;

    @RequestMapping(value = "/api/service/biz/aams/query/queryPageParamMap",method = {RequestMethod.POST })
    @ResponseBody
    PageResponse<Map<String, Object>> queryPageParamMap(@RequestParam("page") int page,@RequestParam("size") int size,@RequestParam("sql") String sql,@RequestBody Map<String, Object> paramMap);

    @RequestMapping(value = "/api/service/biz/aams/query/queryPageParamObject",method = {RequestMethod.POST })
    @ResponseBody
    PageResponse<Map<String, Object>> queryPageParamObject(@RequestParam("page") int page,@RequestParam("size") int size,@RequestParam("sql") String sql,@RequestParam("args") Object... args);

    @RequestMapping(value = "/api/service/biz/aams/query/queryPageParamMapT",method = {RequestMethod.POST })
    @ResponseBody
    <T> PageResponse<T> queryPageParamMapT(@RequestParam("entityName") String entityName, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sql") String sql, @RequestBody Map<String, ?> paramMap) throws Exception;

    @RequestMapping(value = "/api/service/biz/aams/query/queryPageParamObjectT",method = {RequestMethod.POST })
    @ResponseBody
    <T> PageResponse<T> queryPageParamObjectT(@RequestParam("entityName") String entityName, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sql") String sql, @RequestParam("args") Object... args) throws Exception;


}
