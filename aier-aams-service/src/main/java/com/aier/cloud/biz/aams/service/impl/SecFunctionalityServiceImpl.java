package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.biz.aams.dao.SecFunctionalityMapper;
import com.aier.cloud.biz.aams.entity.SecFunctionality;
import com.aier.cloud.biz.aams.service.SecFunctionalityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Service
public class SecFunctionalityServiceImpl extends ServiceImpl<SecFunctionalityMapper, SecFunctionality> implements SecFunctionalityService {

    @Override
    public List<SecFunctionality> queryJoinRoles(String secRoleIds) {
        return this.baseMapper.queryJoinRoles(secRoleIds);
    }
}
