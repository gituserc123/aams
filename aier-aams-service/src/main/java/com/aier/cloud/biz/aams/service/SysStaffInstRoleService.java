package com.aier.cloud.biz.aams.service;

import com.aier.cloud.aams.api.request.condition.AuthorizeAamsCondition;
import com.aier.cloud.biz.aams.entity.SysStaffInstRole;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 柯南
 * @since 2024-05-08 09:50:12
 */
public interface SysStaffInstRoleService extends IService<SysStaffInstRole> {

    List<SysStaffInstRole> getStaffRoleInstList(Long roleId, Long institutionId, Long staffId);

    List<Map<String, Object>> getStaffRoleInstByCond(Page<Map<String, Object>> page, AuthorizeAamsCondition cond);

    @Transactional(rollbackFor=Throwable.class)
    Boolean batchUpdateStaffInstRoleByRole(List<Long> staffIds, Long instId, Long roleId);

    List<Map<String, Object>> getStaffRoleInstInfo(AuthorizeAamsCondition cond);

    Boolean batchUpdateStaffInstRoleByStaff(Long staffId, Long instId, List<Long> roleIds);

    Boolean batchDeleteStaffInstRoleByStaff(Long staffId, Long instId, List<Long> roleIds);

}
