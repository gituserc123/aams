package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.aams.api.request.domain.SecUser;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.biz.aams.dao.RiskQuestionStandardMapper;
import com.aier.cloud.biz.aams.entity.RiskQuestionStandard;
import com.aier.cloud.biz.aams.service.RiskQuestionStandardService;
import com.aier.cloud.center.common.context.AierUser;
import com.aier.cloud.center.common.context.UserContext;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Service
public class RiskQuestionStandardServiceImpl extends ServiceImpl<RiskQuestionStandardMapper, RiskQuestionStandard> implements RiskQuestionStandardService {

    @Override
    public List<RiskQuestionStandard> queryRiskQuestionStandard(Long riskId,  Integer riskQuestionStandardIsDlt){
        EntityWrapper<RiskQuestionStandard> em = new EntityWrapper<>();
        // 设置需要查询的列
        // 通过mybatis.configuration.map-underscore-to-camel-case=true,直接都转驼峰了
        em.setSqlSelect(" * ");
        // 设置查询条件
        em.eq("RiskId", riskId);
        if(riskQuestionStandardIsDlt!=null){
            em.eq("RiskQuestionStandardIsDlt", riskQuestionStandardIsDlt);
        }
        List<RiskQuestionStandard> riskScoreStandards = this.baseMapper.selectList(em);
        return riskScoreStandards;
    }

    @Override
    public Object save(RiskQuestionStandard riskQuestionStandard){
        try{
            AierUser aamsUser = UserContext.getUser();
            SecUser secUser = JSON.parseObject(aamsUser.getMasterSlaveCookie().get("secUser"), SecUser.class);
            if(riskQuestionStandard.getRiskQuestionStandardId() == null){
                riskQuestionStandard.setRiskquestionstandardcreateTime(new Date());
                riskQuestionStandard.setRiskquestionstandardcreateUser(String.valueOf(secUser.getSecuserid()));
                riskQuestionStandard.setRiskQuestionStandardIsDlt(false);
                riskQuestionStandard.setRiskQuestionStandardValue("null");
                riskQuestionStandard.setRiskQuestionStandardRemark("null");
                // 判断RiskQuestionStandardCode的值
                Integer rsCount = this.baseMapper.selectCount(new EntityWrapper<RiskQuestionStandard>().where("RiskId={0}", riskQuestionStandard.getRiskId()));
                riskQuestionStandard.setRiskQuestionStandardCode(rsCount+1);
            }
            riskQuestionStandard.setRiskquestionstandardupdateTime(new Date());
            riskQuestionStandard.setRiskquestionstandardupdateUser(String.valueOf(secUser.getSecuserid()));
            this.insertOrUpdate(riskQuestionStandard);
        }catch (Exception e){
            throw new BizException("保存问题标准失败",e);
        }
        return riskQuestionStandard;
    }

    @Override
    public int deleteRiskQuestionStandard(Long riskQuestionStandardId){
        return this.baseMapper.deleteRiskQuestionStandard(riskQuestionStandardId);
    }

}
