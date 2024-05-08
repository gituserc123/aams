package com.aier.cloud.biz.aams.service;

import com.aier.cloud.biz.aams.entity.SecUser;
import com.baomidou.mybatisplus.service.IService;

public interface SecUserService  extends IService<SecUser> {
    SecUser selectByPrimaryKey(Long secuserid);
}
