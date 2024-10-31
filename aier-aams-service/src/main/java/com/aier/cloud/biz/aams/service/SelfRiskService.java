package com.aier.cloud.biz.aams.service;

import com.aier.cloud.aams.api.request.condition.SelfRiskCondition;
import com.aier.cloud.biz.aams.entity.SelfRisk;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
public interface SelfRiskService extends IService<SelfRisk> {

    List<SelfRisk> getAll(Page<Map<String, Object>> page, SelfRiskCondition cond);

    SelfRisk save(SelfRisk selfRisk);

}
