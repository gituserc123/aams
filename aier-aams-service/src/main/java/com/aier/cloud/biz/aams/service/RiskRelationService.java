package com.aier.cloud.biz.aams.service;

import com.aier.cloud.biz.aams.entity.RiskRelation;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
public interface RiskRelationService extends IService<RiskRelation> {

    boolean saveRiskRelation(RiskRelation riskRelation);
}
