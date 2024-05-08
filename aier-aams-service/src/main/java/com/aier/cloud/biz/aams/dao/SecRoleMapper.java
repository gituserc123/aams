package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.biz.aams.entity.SecRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Mapper
public interface SecRoleMapper extends BaseMapper<SecRole> {


    /**
     * 查询角色
     * @param r
     * @return
     */
    List getForTree(SecRole r);

    /**
     * 获取当前用户当前医院的角色组
     * @param staffId 用户id
     * @param instId 医院id
     * @param platformCode
     * @return
     */
    List<Map<String,Object>> selectRoleTreeByStaffInst(@Param("staffId") Long staffId, @Param("instId") Long instId, @Param("platformCode") String platformCode);

    /**
     * 获取角色树
     * @param staffId
     * @param instId
     * @param platformCode
     * @param roleType
     * @return
     */
    List<Map<String,Object>> selectRoleTreeByStaffInstHosp(@Param("staffId") Long staffId, @Param("instId") Long instId, @Param("roleType") Integer roleType, @Param("platformCode") String platformCode);

    /**
     * 查询角色
     * @param r
     * @return
     */
    List getForRoleType(SecRole r);


    /**
     * 根据登录账号获取其对应的所有权限
     * @return
     */
    List<String> selectRolesByStaff(@Param("staffId") Long staffId, @Param("instId") Long instId);

}
