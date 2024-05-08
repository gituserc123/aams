package com.aier.cloud.biz.aams.service;

import com.aier.cloud.basic.api.response.domain.sys.RolePermission;
import com.aier.cloud.biz.aams.entity.SysRolePermission;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 角色操作资源表 (角色与操作菜单，按钮的关联关系) 服务类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {



    /**
     * 获取角色操作列表
     * @param permissionList
     * @return
     */
    List<com.aier.cloud.basic.api.response.domain.sys.RolePermission> selectRoleListByPermissions(List<Long> permissionList);

}
