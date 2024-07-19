package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.aams.api.request.condition.SelfRiskCondition;
import com.aier.cloud.biz.aams.dao.SelfRiskMapper;
import com.aier.cloud.biz.aams.entity.SelfRisk;
import com.aier.cloud.biz.aams.service.SelfRiskService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}
