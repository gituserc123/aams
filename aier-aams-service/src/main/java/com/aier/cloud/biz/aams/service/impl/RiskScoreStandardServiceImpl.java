package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.aams.api.request.domain.SecUser;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.biz.aams.dao.QueryMapper;
import com.aier.cloud.biz.aams.dao.RiskScoreStandardMapper;
import com.aier.cloud.biz.aams.entity.RiskScoreStandard;
import com.aier.cloud.biz.aams.entity.SysModule;
import com.aier.cloud.biz.aams.service.RiskScoreStandardService;
import com.aier.cloud.center.common.context.AierUser;
import com.aier.cloud.center.common.context.UserContext;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Service
public class RiskScoreStandardServiceImpl extends ServiceImpl<RiskScoreStandardMapper, RiskScoreStandard> implements RiskScoreStandardService {

    @Resource
    private QueryMapper queryMapper;

    @Override
    public List<RiskScoreStandard> queryRiskScoreStandard(Long riskScoreStandardManualId,String riskScoreStandardType,Integer riskScoreStandardIsDlt){
        EntityWrapper<RiskScoreStandard> em = new EntityWrapper<>();
        // 设置需要查询的列
        // 通过mybatis.configuration.map-underscore-to-camel-case=true,直接都转驼峰了
        em.setSqlSelect("RiskScoreStandardId","RiskScoreStandardManualId","RiskScoreStandardType","RiskScoreStandardScore","RiskScoreStandardDesc","RiskScoreStandardCode","RiskScoreStandardParentCode","RiskScoreStandardValue","RiskScoreStandardIsDlt","RiskScoreStandardCreate_user","RiskScoreStandardCreate_time","RiskScoreStandardUpdate_User","RiskScoreStandardUpdate_time");
        // 设置查询条件
        em.eq("RiskScoreStandardManualId", riskScoreStandardManualId);
        em.eq("RiskScoreStandardType", riskScoreStandardType);
        if(riskScoreStandardIsDlt!=null){
            em.eq("RiskScoreStandardIsDlt", riskScoreStandardIsDlt);
        }
        List<RiskScoreStandard> riskScoreStandards = this.baseMapper.selectList(em);
        return riskScoreStandards;
    }

    @Override
    public int deleteRiskScoreStandard(Long riskScoreStandardId){
        return this.baseMapper.deleteRiskScoreStandard(riskScoreStandardId);
    }

    @Override
    public Object save(RiskScoreStandard riskScoreStandard){
        try{
            AierUser aamsUser = UserContext.getUser();
            SecUser secUser = JSON.parseObject(aamsUser.getMasterSlaveCookie().get("secUser"), SecUser.class);
            if(riskScoreStandard.getRiskScoreStandardId() == null){
                riskScoreStandard.setRiskscorestandardcreateTime(new Date());
                riskScoreStandard.setRiskscorestandardcreateUser(String.valueOf(secUser.getSecuserid()));
                riskScoreStandard.setRiskScoreStandardIsDlt(false);
                // 判断RiskScoreStandardCode的值
                //Integer rsCount = this.baseMapper.selectCount(new EntityWrapper<RiskScoreStandard>().where("RiskScoreStandardManualId={0} and RiskScoreStandardType='1'", riskScoreStandard.getRiskScoreStandardManualId()));
                Integer rsCount = queryMapper.queryCountSql(" count(*) ", "RiskScoreStandard"," RiskScoreStandardManualId = " + riskScoreStandard.getRiskScoreStandardManualId() + " and RiskScoreStandardType='1' ");
                riskScoreStandard.setRiskScoreStandardCode(rsCount+1);
            }
            riskScoreStandard.setRiskscorestandardupdateTime(new Date());
            riskScoreStandard.setRiskscorestandardupdateUser(String.valueOf(secUser.getSecuserid()));
            this.insertOrUpdate(riskScoreStandard);
        }catch (Exception e){
            throw new BizException("保存扣分标准失败",e);
        }
        // 为什么要返回riskScoreStandard？如果存在对应自评手册，且RiskScoreStandardCode相同，则用于更新自评手册;
        // RiskScoreStandardCode在自评手册表不存在，则插入相同RiskScoreStandardCode的自评手册。
        return riskScoreStandard;
    }

    @Override
    public Object saveSelfRiskScoreStandard(RiskScoreStandard riskScoreStandard){
        try{
            // 根据ManualId和类型查询
            List<RiskScoreStandard> selfRs = this.baseMapper.selectList(new EntityWrapper<RiskScoreStandard>().where("RiskScoreStandardManualId={0} and RiskScoreStandardType='2'", riskScoreStandard.getRiskScoreStandardManualId()));
            if(Objects.nonNull(selfRs) && selfRs.size()>0){
                Optional<RiskScoreStandard> opnlRs = selfRs.stream()
                        .filter(itemRs -> itemRs.getRiskScoreStandardCode().intValue() == riskScoreStandard.getRiskScoreStandardCode().intValue())
                        .findFirst();
                // 如果存在RiskScoreStandardCode相同的记录,修改RiskScoreStandardId后,再更新
                if(opnlRs.isPresent()){
                    riskScoreStandard.setRiskScoreStandardId(opnlRs.get().getRiskScoreStandardId());
                }
            }
            this.insertOrUpdate(riskScoreStandard);
        }catch (Exception e){
            throw new BizException("保存自评手册扣分标准失败",e);
        }
        return true;
    }

    @Override
    public Object saveByRisk(RiskScoreStandard riskScoreStandard){
        try{
            AierUser aamsUser = UserContext.getUser();
            SecUser secUser = JSON.parseObject(aamsUser.getMasterSlaveCookie().get("secUser"), SecUser.class);
            if(riskScoreStandard.getRiskScoreStandardId() == null){
                riskScoreStandard.setRiskscorestandardcreateTime(new Date());
                riskScoreStandard.setRiskscorestandardcreateUser(String.valueOf(secUser.getSecuserid()));
            }
            riskScoreStandard.setRiskscorestandardupdateTime(new Date());
            riskScoreStandard.setRiskscorestandardupdateUser(String.valueOf(secUser.getSecuserid()));
            this.insertOrUpdate(riskScoreStandard);
        }catch (Exception e){
            throw new BizException("保存扣分标准失败",e);
        }
        return riskScoreStandard;
    }



}
