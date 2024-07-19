package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.aams.api.request.condition.SelfRiskCondition;
import com.aier.cloud.biz.aams.entity.SelfRisk;
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
public interface SelfRiskMapper extends BaseMapper<SelfRisk> {
    List<SelfRisk> getAll(Page<Map<String, Object>> page, @Param("cond") SelfRiskCondition cond);
}
