package com.aier.cloud.biz.aams.service;

import com.aier.cloud.aams.api.request.condition.AuditRecordCondition;
import com.aier.cloud.biz.aams.entity.AuditRecord;
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
public interface AuditRecordService extends IService<AuditRecord> {
    List<AuditRecord> getAllFxts(Page<Map<String, Object>> page, AuditRecordCondition cond);

    List<AuditRecord> getAuditPlanList(Page<Map<String, Object>> page, AuditRecordCondition cond);

}
