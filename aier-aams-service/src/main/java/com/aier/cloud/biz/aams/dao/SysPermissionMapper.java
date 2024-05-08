package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.biz.aams.entity.SysPermission;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 操作表（需要权限管理的页面对象如按钮，图片，文件等） Mapper 接口
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 获取当前所有操作集合
     * @param
     * @param platformCode
     * @return
     */
    List<SysPermission> selectAllList(@Param("platformCode") String platformCode);

    /**
     * 获取用户当前站点，角色集合下的所有操作权限
     * 以后优化可以拆分为两次查询
     * @param staffId 用户id
     * @param instId  机构id，是指的医院，不是科室
     * @param platformCode  平台code
     * @param platformCode
     * @return List<Permission>
     */
    List<SysPermission> selectListByUserAndInst(@Param("staffId") Long staffId, @Param("instId") Long instId, @Param("platformCode") String platformCode);

    /**
     * 门户登录后的用户鉴权
     * @param staffId
     * @param instId
     * @return
     */
    List<SysPermission> selectAllListByPortal(@Param("staffId") Long staffId, @Param("instId") Long instId);

    /**
     * 获取用户当前站点，角色集合下的所有操作权限
     * 以后优化可以拆分为两次查询
     * @param staffId 用户id
     * @param instId  机构id，是指的医院，不是科室
     * @param platformCode
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> selectMapByUserAndInst(@Param("staffId") Long staffId, @Param("instId") Long instId, @Param("platformCode") String platformCode);

    /**
     * 获取用户当前站点，角色集合下的所有操作权限
     * 以后优化可以拆分为两次查询
     * @param staffId 用户id
     * @param instId  机构id，是指的医院，不是科室
     * @param platformCode
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> selectModuleMapsByUserAndInst(@Param("staffId") Long staffId, @Param("instId") Long instId,@Param("platformCode") String platformCode);

    /**
     * 根据用户id获取用户在所有平台的权限数量
     * @param staffId
     * @return
     */
    List<String> selectPlatFormListByStaff(@Param("staffId") Long staffId);
}
