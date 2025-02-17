package com.aier.cloud.ui.biz.aams.controller;


import com.aier.cloud.aams.api.request.condition.OrgMasterCondition;
import com.aier.cloud.aams.api.request.domain.OrgMaster;
import com.aier.cloud.basic.web.controller.BaseController;
import com.aier.cloud.ui.biz.aams.feign.QueryMapperFeignService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ui/aams/orgMaster")
public class OrgMasterUiController extends BaseController {

    @Autowired
    private QueryMapperFeignService orgMasterFeignService;

    @RequestMapping(value = "/getOrgMasterByCond", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    protected List<OrgMaster> getOrgMasterByCond(OrgMasterCondition cond){
        StringBuilder  sql = new StringBuilder();
        sql.append("select * from OrgMaster where 1=1 ");
        Map<String, Object> params = Maps.newHashMap();
        if(StringUtils.isNotEmpty(cond.getOrgMasterName())){
            params.put("orgMasterName", cond.getOrgMasterName());
            sql.append(" and OrgMasterName like '%:orgMasterName%' ");
        }

        List<OrgMaster> ldLists = orgMasterFeignService.commonQueryListBeanByMap(sql.toString(), "OrgMaster", params);
        return ldLists;
    }
}
