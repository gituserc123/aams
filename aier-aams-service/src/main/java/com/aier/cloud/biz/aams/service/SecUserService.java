package com.aier.cloud.biz.aams.service;

import com.aier.cloud.aams.api.request.condition.SecUserCondition;
import com.aier.cloud.biz.aams.entity.SecUser;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

public interface SecUserService  extends IService<SecUser> {
    SecUser selectByPrimaryKey(Long secuserid);

    List<SecUser> getSecUserByCond(SecUserCondition cond);
}
