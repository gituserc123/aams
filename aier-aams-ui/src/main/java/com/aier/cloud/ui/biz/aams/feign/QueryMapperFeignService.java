package com.aier.cloud.ui.biz.aams.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
