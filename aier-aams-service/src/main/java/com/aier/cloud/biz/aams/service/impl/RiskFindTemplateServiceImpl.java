package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.aams.api.request.domain.SecUser;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.biz.aams.dao.RiskFindTemplateMapper;
import com.aier.cloud.biz.aams.entity.RiskFindTemplate;
import com.aier.cloud.biz.aams.service.RiskFindTemplateService;
import com.aier.cloud.center.common.context.AierUser;
import com.aier.cloud.center.common.context.UserContext;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Service
public class RiskFindTemplateServiceImpl extends ServiceImpl<RiskFindTemplateMapper, RiskFindTemplate> implements RiskFindTemplateService {

    @Override
    public List<Map<String,Object>> getRiskTemplatesByRiskId(Long riskId){
        EntityWrapper em = new EntityWrapper<>();
        // 设置需要查询的列
        // 通过mybatis.configuration.map-underscore-to-camel-case=true,直接都转驼峰了
        em.setSqlSelect("RiskFindTemplateId", "RiskFindTemplateDetail", "RiskId", "RiskFindTemplateCreate_time");
        // 设置查询条件
        em.eq("RiskId", riskId);
        em.eq("RiskFindTemplateIsdlt", 0);
        List<Map<String,Object>> templates = this.baseMapper.selectMaps(em);
        return templates;
    }

    @Override
    public Map<String, Object> save(RiskFindTemplate riskFindTemplate){
        Map<String, Object> result = Maps.newHashMap();
        try{
            AierUser aamsUser = UserContext.getUser();
            SecUser secUser = JSON.parseObject(aamsUser.getMasterSlaveCookie().get("secUser"), SecUser.class);
            if(null == riskFindTemplate.getRiskFindTemplateId()){
                riskFindTemplate.setRiskfindtemplatecreateTime(new Date());
                riskFindTemplate.setRiskfindtemplatecreateUser(String.valueOf(secUser.getSecuserid()));
                riskFindTemplate.setRiskFindTemplateIsdlt(false);
            }
            riskFindTemplate.setRiskfindtemplateupdateTime(new Date());
            riskFindTemplate.setRiskfindtemplateupdateUser(String.valueOf(secUser.getSecuserid()));
            this.insertOrUpdate(riskFindTemplate);
            result.put("msg","成功");
            result.put("code","200");
        }catch (Exception e){
            throw new BizException("保存失败：" + e.getMessage());
        }

        return result;
    }

}
