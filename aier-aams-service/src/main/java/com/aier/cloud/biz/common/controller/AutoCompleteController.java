package com.aier.cloud.biz.common.controller;

import com.aier.cloud.basic.starter.service.controller.BaseController;
import com.aier.cloud.basic.starter.service.controller.TransCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/common/autoComplete")
public class AutoCompleteController extends BaseController {

    @Resource
    private TransCodeService as;

    @RequestMapping(value = "/getList")
    @ResponseBody
    public List<Map<String,Object>> getAutoCompleteService(@RequestParam("type")String type,@RequestParam(value="filter", required = false)String filter,@RequestParam(value="code", required = false)String code ) {
        return (List)as.getList(type, filter, code);
    }

}
