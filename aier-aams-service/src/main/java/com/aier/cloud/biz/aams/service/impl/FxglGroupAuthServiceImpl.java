package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.biz.aams.dao.FxglGroupAuthMapper;
import com.aier.cloud.biz.aams.entity.FxglGroupAuth;
import com.aier.cloud.biz.aams.service.FxglGroupAuthService;
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
public class FxglGroupAuthServiceImpl extends ServiceImpl<FxglGroupAuthMapper, FxglGroupAuth> implements FxglGroupAuthService {

    @Override
    public List<Map<String, Object>> selectBySecUserId(Long secUserId) {
        return this.baseMapper.selectBySecUserId(secUserId);
    }
}
