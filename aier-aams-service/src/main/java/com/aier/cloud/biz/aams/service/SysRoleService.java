package com.aier.cloud.biz.aams.service;

import com.aier.cloud.basic.api.request.condition.sys.RoleCondition;
import com.aier.cloud.biz.aams.entity.SysRole;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * getPermByRoleId
     * @param roleId
     * @param isEdit
     * @param platformCode
     * @return
     */
    List getPermByRoleId(Long roleId, boolean isEdit, String platformCode);

    /**
     * updateRolePerm
     * @param rc
     * @return
     */
    void updateRolePerm(RoleCondition rc);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    void deleteRole(Long roleId);

    /**
     * 获取真的平台编码
     * @param code
     * @return
     */
    String getTruePlatformCode(String code);

    /**
     * 查询角色
     * @param r
     * @return
     */
    List getForTree(SysRole r);

    /**
     * 获取当前用户当前医院的角色组
     * @param staffId 用户id
     * @param instId 医院id
     * @param platformCode
     * @return
     */
    List<Map<String,Object>> selectRoleTreeByStaffInst(Long staffId, Long instId, String platformCode);

    /**
     * 获取当前用户当前医院的角色组
     * @param staffId
     * @param instId
     * @param roleType
     * @param platformCode
     * @return
     */
    List<Map<String,Object>> selectRoleTreeByStaffInstHosp(Long staffId, Long instId, Integer roleType, String platformCode);

    void updateRole(SysRole role);
}
