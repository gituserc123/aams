package com.aier.cloud.biz.aams.service;

import com.aier.cloud.biz.aams.entity.RiskScoreStandard;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
public interface RiskScoreStandardService extends IService<RiskScoreStandard> {
    List<RiskScoreStandard> queryRiskScoreStandard(Long riskScoreStandardManualId, String riskScoreStandardType, Integer riskScoreStandardIsDlt);

    Object save(RiskScoreStandard riskScoreStandard);

    Object saveSelfRiskScoreStandard(RiskScoreStandard riskScoreStandard);

    int deleteRiskScoreStandard(Long riskScoreStandardId);

    Object saveByRisk(RiskScoreStandard riskScoreStandard);

}
