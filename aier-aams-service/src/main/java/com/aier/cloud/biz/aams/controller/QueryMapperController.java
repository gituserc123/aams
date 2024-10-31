package com.aier.cloud.biz.aams.controller;

import com.aier.cloud.basic.starter.service.controller.BaseController;
import com.aier.cloud.biz.aams.dao.QueryMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Controller
@RequestMapping("/api/service/biz/aams/query")
public class QueryMapperController extends BaseController {

    @Resource
    private QueryMapper queryMapper;

    @RequestMapping(value = "/queryCount")
    @ResponseBody
    public Integer queryCount(@RequestParam("queryFeild") String queryFeild, @RequestParam("table") String table, @RequestParam("cond") String cond) {
        Integer count = queryMapper.queryCountSql(queryFeild,table,cond);
        return count;
    }

    @RequestMapping(value = "/queryList")
    @ResponseBody
    public List<Map<String, Object>> queryList(@RequestParam("queryFeild") String queryFeild, @RequestParam("table") String table, @RequestParam("cond") String cond){
        return queryMapper.queryListSql(queryFeild,table,cond);
    }

}
