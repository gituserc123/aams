package com.aier.cloud.biz.aams.service;

import com.aier.cloud.aams.api.request.condition.RiskCondition;
import com.aier.cloud.biz.aams.entity.Risk;
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
public interface RiskService extends IService<Risk> {
    List<Risk> getAll(Page<Map<String, Object>> page, RiskCondition cond);

    Map<String,Object> save(Risk risk);
}
