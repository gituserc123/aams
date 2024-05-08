package com.aier.cloud.biz.aams.service;

import com.aier.cloud.biz.aams.entity.SysPermission;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作表（需要权限管理的页面对象如按钮，图片，文件等） 服务类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
public interface SysPermissionService extends IService<SysPermission> {
    /**
     * 获取所有操作集合
     * @param platformCode
     * @return
     */
    List<SysPermission> selectAllList(String platformCode);

    /**
     * 获取用户当前站点，角色集合下的所有操作权限
     * 以后优化可以拆分为两次查询
     * @param staffId 用户id
     * @param instId  机构id，是指的医院，不是科室
     * @param platformCode 平台code
     * @return List<Permission>
     */
    List<SysPermission> selectListByUserAndInst(Long staffId, Long instId, String platformCode);

    /**
     * 查看当前用户在某医院的所有访问权限, 以树形结构返回，如：
     * [{
     "id":2,
     "pid":1,
     "name":"系统管理",
     "status":true
     },{
     "id":21,
     "pid":2,
     "name":"用户管理",
     "status":true,
     "power": [
     {"id":211,"text":"增加","status":true},
     {"id":212,"text":"修改","status":true},
     {"id":213,"text":"删除","status":true},
     {"id":214,"text":"重置密码","status":true},
     {"id":215,"text":"更新状态","status":true},
     {"id":216,"text":"设置权限","status":true}
     ]
     }]
     * @param staffId
     * @param instId
     * @param platformCode
     * @return
     */
    List<Map<String, Object>> lookUpAuthorize(Long staffId, Long instId, String platformCode);

    /**
     * 根据用户id获取用户在所有平台的权限数量
     * @param staffId
     * @return
     */
    List<String> selectPlatFormListByStaff(Long staffId);
}
