package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.aams.api.request.condition.SecUserCondition;
import com.aier.cloud.biz.aams.entity.SecUser;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SecUserMapper  extends BaseMapper<SecUser> {

    SecUser selectByPrimaryKey(Long secuserid);

    List<SecUser> getSecUserByCond(@Param("cond") SecUserCondition cond);
}