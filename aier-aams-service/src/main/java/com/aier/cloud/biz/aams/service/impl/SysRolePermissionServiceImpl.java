package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.biz.aams.dao.SysRolePermissionMapper;
import com.aier.cloud.biz.aams.entity.SysRolePermission;
import com.aier.cloud.biz.aams.service.SysRolePermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色操作资源表 (角色与操作菜单，按钮的关联关系) 服务实现类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

    @Override
    public List<com.aier.cloud.basic.api.response.domain.sys.RolePermission> selectRoleListByPermissions(List<Long> permissionList) {
        return this.baseMapper.selectRoleListByPermissions(permissionList);
    }
}
