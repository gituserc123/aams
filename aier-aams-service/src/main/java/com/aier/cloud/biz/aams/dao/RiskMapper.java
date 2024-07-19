package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.aams.api.request.condition.RiskCondition;
import com.aier.cloud.biz.aams.entity.Risk;
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
public interface RiskMapper extends BaseMapper<Risk> {

    List<Risk> getAll(Page<Map<String, Object>> page, @Param("cond") RiskCondition cond);

}
