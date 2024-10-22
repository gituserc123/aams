package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.biz.aams.entity.RiskQuestionStandard;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Mapper
public interface RiskQuestionStandardMapper extends BaseMapper<RiskQuestionStandard> {

    @Delete("Update RiskQuestionStandard set RiskQuestionStandardIsDlt=1 WHERE RiskQuestionStandardId = ${riskQuestionStandardId}")
    int deleteRiskQuestionStandard(@Param("riskQuestionStandardId") Long riskQuestionStandardId);
}
