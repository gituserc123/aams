package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.aams.api.request.condition.AuditRecordCondition;
import com.aier.cloud.biz.aams.entity.AuditRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Mapper
public interface AuditRecordMapper extends BaseMapper<AuditRecord> {
    List<AuditRecord> getAllFxts(Page<Map<String, Object>> page, @Param("cond")AuditRecordCondition cond);

    List<AuditRecord> getAuditPlanList(Page<Map<String, Object>> page, @Param("cond")AuditRecordCondition cond);
}
