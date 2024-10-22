package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.aams.api.request.condition.AuthorizeAamsCondition;
import com.aier.cloud.biz.aams.dao.SysStaffInstRoleMapper;
import com.aier.cloud.biz.aams.entity.SysStaffInstRole;
import com.aier.cloud.biz.aams.service.SysStaffInstRoleService;
import com.aier.cloud.center.common.context.UserContext;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 柯南
 * @since 2024-05-08 09:50:12
 */
@Service
public class SysStaffInstRoleServiceImpl extends ServiceImpl<SysStaffInstRoleMapper, SysStaffInstRole> implements SysStaffInstRoleService {

    @Override
    public List<SysStaffInstRole> getStaffRoleInstList(Long roleId, Long institutionId, Long staffId) {
        return this.baseMapper.getStaffRoleInstList(roleId,institutionId,staffId);
    }

    @Override
    public List<Map<String, Object>> getStaffRoleInstByCond(Page<Map<String, Object>> page, AuthorizeAamsCondition cond) {
        return this.baseMapper.getStaffRoleInstByCond(page,cond);
    }

    @Override
    public List<Map<String, Object>> getStaffRoleInstInfo(AuthorizeAamsCondition cond) {
        return this.baseMapper.getStaffRoleInstInfo(cond);
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public Boolean batchUpdateStaffInstRoleByRole(List<Long> staffIds, Long instId, Long roleId){

        List<SysStaffInstRole> list = Lists.newArrayList();
        staffIds.stream().forEach(staffId -> {
            SysStaffInstRole staffInstRole = new SysStaffInstRole();
            staffInstRole.setRoleId(roleId);
            staffInstRole.setInstitutionId(instId);
            staffInstRole.setStaffId(staffId);
            staffInstRole.setCreator(UserContext.getUserId());
            staffInstRole.setModifer(UserContext.getUserId());
            staffInstRole.setCreateDate(new Date());
            staffInstRole.setModifyDate(new Date());

            list.add(staffInstRole);
        });
        return CollectionUtils.isEmpty(list) ? true : insertBatch(list);
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public Boolean batchUpdateStaffInstRoleByStaff(Long staffId, Long instId, List<Long> roleIds){

        List<SysStaffInstRole> list = Lists.newArrayList();
        roleIds.stream().forEach(roleId -> {
            SysStaffInstRole staffInstRole = new SysStaffInstRole();
            staffInstRole.setRoleId(roleId);
            staffInstRole.setInstitutionId(instId);
            staffInstRole.setStaffId(staffId);
            staffInstRole.setCreator(UserContext.getUserId());
            staffInstRole.setModifer(UserContext.getUserId());
            staffInstRole.setCreateDate(new Date());
            staffInstRole.setModifyDate(new Date());

            list.add(staffInstRole);
        });
        return CollectionUtils.isEmpty(list) ? true : insertBatch(list);
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public Boolean batchDeleteStaffInstRoleByStaff(Long staffId, Long instId, List<Long> roleIds){
        try{
            //roleIds.stream().forEach(roleId -> {
                EntityWrapper<SysStaffInstRole> wrapper = new EntityWrapper<>();
                //wrapper.eq("role_id", roleId);
                wrapper.eq("staff_id", staffId);
                wrapper.eq("institution_id", instId);
                this.delete(wrapper);
            //});
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
