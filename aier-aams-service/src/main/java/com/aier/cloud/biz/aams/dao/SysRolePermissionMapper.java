package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.basic.api.response.domain.sys.RolePermission;
import com.aier.cloud.biz.aams.entity.SysRolePermission;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 角色操作资源表 (角色与操作菜单，按钮的关联关系) Mapper 接口
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {


    /**
     * 获取角色操作列表
     * @param permissionList
     * @return
     */
    List<com.aier.cloud.basic.api.response.domain.sys.RolePermission> selectRoleListByPermissions(@Param("permissionList") List<Long> permissionList);
}
