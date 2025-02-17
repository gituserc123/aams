package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.aams.api.request.condition.AuditRecordCondition;
import com.aier.cloud.biz.aams.dao.AuditRecordMapper;
import com.aier.cloud.biz.aams.entity.AuditRecord;
import com.aier.cloud.biz.aams.service.AuditRecordService;
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
public class AuditRecordServiceImpl extends ServiceImpl<AuditRecordMapper, AuditRecord> implements AuditRecordService {


    @Override
    public List<AuditRecord> getAllFxts(Page<Map<String, Object>> page, AuditRecordCondition cond) {
        return this.baseMapper.getAllFxts(page,cond);
    }

    @Override
    public List<AuditRecord> getAuditPlanList(Page<Map<String, Object>> page, AuditRecordCondition cond) {
        return this.baseMapper.getAuditPlanList(page,cond);
    }
}
