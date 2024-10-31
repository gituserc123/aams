package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.aams.api.request.condition.SelfRiskCondition;
import com.aier.cloud.aams.api.request.domain.SecUser;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.biz.aams.dao.SelfRiskMapper;
import com.aier.cloud.biz.aams.entity.RiskQuestionStandard;
import com.aier.cloud.biz.aams.entity.SelfRisk;
import com.aier.cloud.biz.aams.service.SelfRiskService;
import com.aier.cloud.center.common.context.AierUser;
import com.aier.cloud.center.common.context.UserContext;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class SelfRiskServiceImpl extends ServiceImpl<SelfRiskMapper, SelfRisk> implements SelfRiskService {

    @Override
    public List<SelfRisk> getAll(Page<Map<String, Object>> page, SelfRiskCondition cond) {
        return this.baseMapper.getAll(page,cond);
    }

    @Override
    public SelfRisk save(SelfRisk selfRisk){
        try{
            AierUser aamsUser = UserContext.getUser();
            SecUser secUser = JSON.parseObject(aamsUser.getMasterSlaveCookie().get("secUser"), SecUser.class);
            if(selfRisk.getSelfRiskId() == null){
                selfRisk.setSelfriskcreateTime(new Date());
                selfRisk.setSelfriskcreateUser(String.valueOf(secUser.getSecuserid()));
                selfRisk.setSelfRiskIsdlt(false);
            }
            selfRisk.setSelfriskupdateTime(new Date());
            selfRisk.setSelfriskupdateUser(String.valueOf(secUser.getSecuserid()));
            this.insertOrUpdate(selfRisk);
        }catch (Exception e){
            throw new BizException("保存问题标准失败",e);
        }
        return selfRisk;
    }
}
