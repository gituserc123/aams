package com.aier.cloud.biz.aams.service.impl;


import com.aier.cloud.aams.api.request.condition.SecUserCondition;
import com.aier.cloud.biz.aams.dao.SecUserMapper;
import com.aier.cloud.biz.aams.entity.SecUser;
import com.aier.cloud.biz.aams.service.SecUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Transactional(rollbackFor=Throwable.class)
public class SecUserServiceImpl  extends ServiceImpl<SecUserMapper, SecUser> implements SecUserService {

    @Override
    public SecUser selectByPrimaryKey(Long secuserid) {
        return this.baseMapper.selectByPrimaryKey(secuserid);
    }

    @Override
    public List<SecUser> getSecUserByCond(@RequestBody SecUserCondition cond) {
        return this.baseMapper.getSecUserByCond(cond);
    }
}
