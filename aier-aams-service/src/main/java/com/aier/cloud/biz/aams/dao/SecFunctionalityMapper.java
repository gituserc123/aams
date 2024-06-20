package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.biz.aams.entity.SecFunctionality;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Mapper
public interface SecFunctionalityMapper extends BaseMapper<SecFunctionality> {

    List<SecFunctionality> queryJoinRoles(@Param("secRoleIds") String secRoleIds);
}
