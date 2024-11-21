package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.biz.aams.dao.RiskRelationMapper;
import com.aier.cloud.biz.aams.entity.RiskRelation;
import com.aier.cloud.biz.aams.service.RiskRelationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Service
public class RiskRelationServiceImpl extends ServiceImpl<RiskRelationMapper, RiskRelation> implements RiskRelationService {

    @Override
    public boolean saveRiskRelation(RiskRelation riskRelation) {
        return this.baseMapper.saveRiskRelation(riskRelation);
    }
}
