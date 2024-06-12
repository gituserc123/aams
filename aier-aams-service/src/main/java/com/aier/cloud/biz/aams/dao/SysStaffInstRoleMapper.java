package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.aams.api.request.condition.AuthorizeAamsCondition;
import com.aier.cloud.biz.aams.entity.SysStaffInstRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
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
 * @since 2024-05-08 09:50:12
 */
@Mapper
public interface SysStaffInstRoleMapper extends BaseMapper<SysStaffInstRole> {
    List<SysStaffInstRole> getStaffRoleInstList(@Param("roleId") Long roleId, @Param("institutionId") Long institutionId, @Param("staffId") Long staffId);

    List<Map<String, Object>> getStaffRoleInstByCond(Page<Map<String, Object>> page, @Param("cond") AuthorizeAamsCondition cond);

    List<Map<String, Object>> getStaffRoleInstInfo(@Param("cond") AuthorizeAamsCondition cond);


}
