package com.aier.cloud.ui.biz.aams.controller;


import com.aier.cloud.aams.api.request.domain.CodeMaster;
import com.aier.cloud.ui.biz.aams.feign.QueryMapperFeignService;
import com.aier.cloud.ui.biz.aams.feign.UserInfoTestFeignService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/ui/aams/manage/test")
public class UserInfoTestUiController {

    @Autowired
    private UserInfoTestFeignService userInfoTestUiFeignService;

    @Autowired
    private QueryMapperFeignService queryMapperFeignService;

    @RequestMapping(value = "/testGetShiroUser",method = { RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String testGetShiroUser(){
        return userInfoTestUiFeignService.testGetShiroUser("测试112");
    }


    @RequestMapping(value = "/getCodeMaster")
    public @ResponseBody List<Map<String, Object>> getCodeMaster() {

        StringBuilder sql = new StringBuilder();
        sql.append("select * from CodeMaster");
        sql.append(" where CodeMasterType = ? ");

        return queryMapperFeignService.commonQueryList(sql.toString(), "AuditRecordType");
    }

    @RequestMapping(value = "/getCodeMasterBean")
    public @ResponseBody List<CodeMaster> getCodeMasterBean() {

        StringBuilder sql = new StringBuilder();
        sql.append("select * from CodeMaster");
        sql.append(" where CodeMasterType = ? ");

        return queryMapperFeignService.commonQueryListBean(sql.toString(), "com.aier.cloud.biz.aams.entity.CodeMaster","AuditRecordType");
    }

    /*
    *
    * http://localhost:8097/ui/aams/manage/test/deleteTest?fId=35&sId=1131&beanClass=AuditFxtsReply
    * */
    @RequestMapping(value = "/deleteTest")
    public @ResponseBody Object deleteTest(String beanClass,String fId,String sId) {
        Map<String,Object> conditions = Maps.newHashMap();
        conditions.put("auditFxtsReplyId",Long.parseLong(fId));
        //conditions.put("RiskRelationRiskId",Long.parseLong(sId));
        return queryMapperFeignService.deleteByMap(beanClass,conditions);
    }

    /*
    *
    * http://localhost:8097/ui/aams/manage/test/insertOrUpdate?fId=1151&sId=1131&beanClass=AuditFxtsReply
    * */
    @RequestMapping(value = "/insertOrUpdate")
    public @ResponseBody Object insertOrUpdate(String beanClass,String fId,String sId) throws Exception {
        Map<String,Object> conditions = Maps.newHashMap();
        conditions.put("auditFxtsReplyId",Long.parseLong("35"));
        conditions.put("auditRecordId",Long.parseLong(fId));
        conditions.put("auditFxtsReplyUser",Long.parseLong(sId));
        conditions.put("auditFxtsReplyText","双方的如特瑞特");
        conditions.put("auditFxtsReplyNum",43);
        conditions.put("auditFxtsReplyUser",Long.parseLong(sId));
        conditions.put("auditFxtsReplyUpdTime",new Date());
        return queryMapperFeignService.insertOrUpdate(conditions,beanClass);
    }
}
