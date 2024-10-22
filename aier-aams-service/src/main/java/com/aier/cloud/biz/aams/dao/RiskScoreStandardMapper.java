package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.biz.aams.entity.RiskScoreStandard;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Mapper
public interface RiskScoreStandardMapper extends BaseMapper<RiskScoreStandard> {

    @Delete("Update RiskScoreStandard set RiskScoreStandardIsDlt=1 WHERE RiskScoreStandardId = ${riskScoreStandardId}")
    int deleteRiskScoreStandard(@Param("riskScoreStandardId") Long riskScoreStandardId);

    @Update({"<script>",
            "update RiskScoreStandard",
            "  <set>",
            "    <if test='riskScoreStandardManualId != null'>RiskScoreStandardManualId=#{riskScoreStandardManualId},</if>",
            "    <if test='riskScoreStandardType != null and riskScoreStandardType != \"\"'>riskScoreStandardType=#{riskScoreStandardType},</if>",
            "    <if test='riskScoreStandardScore != null'>RiskScoreStandardScore=#{riskScoreStandardScore},</if>",
            "    <if test='riskScoreStandardDesc != null and riskScoreStandardDesc != \"\"'>RiskScoreStandardDesc=#{riskScoreStandardDesc}</if>",
            "    <if test='riskScoreStandardCode != null'>RiskScoreStandardCode=#{riskScoreStandardCode},</if>",
            "    <if test='riskScoreStandardIsDlt != null'>RiskScoreStandardIsDlt=#{riskScoreStandardIsDlt},</if>",
            "    <if test='riskscorestandardupdateUser != null and riskscorestandardupdateUser != \"\"'>RiskScoreStandardUpdate_User=#{riskscorestandardupdateUser}</if>",
            "    <if test='riskscorestandardupdateTime != null'>RiskScoreStandardUpdate_time=#{riskscorestandardupdateTime},</if>",
            "  </set>",
            "where RiskScoreStandardId=#{riskScoreStandardId}",
            "</script>"})
    void updateRiskScoreStandard(RiskScoreStandard riskScoreStandard);


}
