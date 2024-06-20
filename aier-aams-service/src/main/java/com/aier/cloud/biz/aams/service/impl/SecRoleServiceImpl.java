package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.biz.aams.dao.SecRoleMapper;
import com.aier.cloud.biz.aams.entity.SecRole;
import com.aier.cloud.biz.aams.service.SecRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Service
public class SecRoleServiceImpl extends ServiceImpl<SecRoleMapper, SecRole> implements SecRoleService {

    @Override
    public List<SecRole> queryRoleByUserId(Long secUserId) {
        return this.baseMapper.queryRoleByUserId(secUserId);
    }
}
