package com.aier.cloud.biz.aams.service;

import com.aier.cloud.biz.aams.entity.RiskQuestionStandard;
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
public interface RiskQuestionStandardService extends IService<RiskQuestionStandard> {

    List<RiskQuestionStandard> queryRiskQuestionStandard(Long riskId, Integer riskQuestionStandardIsDlt);

    Object save(RiskQuestionStandard riskQuestionStandard);

    int deleteRiskQuestionStandard(Long riskQuestionStandardId);

}
