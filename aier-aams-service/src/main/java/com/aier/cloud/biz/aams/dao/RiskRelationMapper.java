package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.biz.aams.entity.RiskRelation;
import com.baomidou.mybatisplus.annotations.SqlParser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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
public interface RiskRelationMapper extends BaseMapper<RiskRelation> {

    @SqlParser(filter = true)
    boolean saveRiskRelation(@Param("riskRelation") RiskRelation riskRelation);

}
