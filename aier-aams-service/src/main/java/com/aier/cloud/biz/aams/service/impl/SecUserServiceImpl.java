package com.aier.cloud.biz.aams.service.impl;


import com.aier.cloud.biz.aams.dao.SecUserMapper;
import com.aier.cloud.biz.aams.entity.SecUser;
import com.aier.cloud.biz.aams.service.SecUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor=Throwable.class)
public class SecUserServiceImpl  extends ServiceImpl<SecUserMapper, SecUser> implements SecUserService {

    @Override
    public SecUser selectByPrimaryKey(Long secuserid) {
        return this.baseMapper.selectByPrimaryKey(secuserid);
    }
}
