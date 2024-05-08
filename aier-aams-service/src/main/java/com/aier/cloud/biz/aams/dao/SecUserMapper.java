package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.biz.aams.entity.SecUser;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecUserMapper  extends BaseMapper<SecUser> {

    SecUser selectByPrimaryKey(Long secuserid);
}