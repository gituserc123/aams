package com.aier.cloud.biz.aams.service;

import com.aier.cloud.biz.aams.entity.RiskFindTemplate;
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
public interface RiskFindTemplateService extends IService<RiskFindTemplate> {
    List<Map<String,Object>> getRiskTemplatesByRiskId(Long riskId);

    Map<String, Object> save(RiskFindTemplate riskFindTemplate);

}
