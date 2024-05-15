package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.basic.common.exception.BizAssert;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.basic.core.base.service.impl.TreeServiceImpl;
import com.aier.cloud.biz.aams.dao.SysModuleMapper;
import com.aier.cloud.biz.aams.entity.SysModule;
import com.aier.cloud.biz.aams.entity.SysPermission;
import com.aier.cloud.biz.aams.service.SysModuleService;
import com.aier.cloud.biz.aams.service.SysPermissionService;
import com.aier.cloud.biz.aams.service.SysRolePermissionService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资源模块表 服务实现类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
@Service
public class SysModuleServiceImpl extends TreeServiceImpl<SysModuleMapper, SysModule> implements SysModuleService {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Override
    public List<SysModule> getAllList(String platformCode) {
        return this.baseMapper.getList(platformCode);
    }

    @Override
    public List<SysModule> getMenuForTree(Long id) {
        BizAssert.notNull(id);
        List<SysModule> list = this.baseMapper.getForTree(id);
        return list;
    }

    @Override
    public boolean insert(SysModule entity) {
        EntityWrapper<SysModule> w = new EntityWrapper<SysModule>();
        w.eq("module_Code", entity.getModuleCode());
        w.eq("platform_code", entity.getPlatformCode());
        BizAssert.empty(baseMapper.selectList(w), BizException.ERROR, "此平台已注册,禁止重复注册!");
        super.insert(entity);
        if (CollectionUtils.isNotEmpty(entity.getPermissions())) {
            for (SysPermission p : entity.getPermissions()) {
                p.setModuleId(entity.getId());
            }
            sysPermissionService.insertBatch(entity.getPermissions());
        }
        return true;
    }

    @Override
    public boolean updateById(SysModule sysModule) {
        BizAssert.notNull(sysModule);
        List<SysPermission> oldPermissions = sysPermissionService.selectList(new EntityWrapper<SysPermission>().eq("module_id", sysModule.getId()));
        List<SysPermission> editPermissions = Lists.newArrayList();
        List<SysPermission> newPermissions = Lists.newArrayList();
        List<Long> delPermissions = Lists.newArrayList();
        // 模块自定义权限，删除过后新增报错，会有validate的验证错误。有删除的，新增的情况
        for (SysPermission permission : sysModule.getPermissions()) {
            // 已选中的
            if (StringUtils.isNotBlank(permission.getPermCode()))
            {
                //新增
                if (null == permission.getId())
                {
                    permission.setModuleId(sysModule.getId());
                    permission.setCreateDate(new Date());
                    permission.setModifyDate(new Date());
                    newPermissions.add(permission);
                } else {
                    permission.setModifyDate(new Date());
                    editPermissions.add(permission);
                }
            }
            else  // 未选中的，将会删除 
            {
                if (permission.getId() != null)
                {
                    for (SysPermission oldPermission : oldPermissions)
                    {
                        if (oldPermission.getId().equals(permission.getId()))
                        {
                            permission = oldPermission;
                            break;
                        }
                    }
                    delPermissions.add(permission.getId());
                }
            }
        }

        //新增的操作
        if (CollectionUtils.isNotEmpty(newPermissions)) {
            sysPermissionService.insertBatch(newPermissions);
        }

        //修改的操作
        if (CollectionUtils.isNotEmpty(editPermissions)) {
            sysPermissionService.updateBatchById(editPermissions);
        }

        if (CollectionUtils.isNotEmpty(delPermissions)) {
            List<com.aier.cloud.basic.api.response.domain.sys.RolePermission> rpList = sysRolePermissionService.selectRoleListByPermissions(delPermissions);
            // 如果有角色和操作管理，给提示给操作管理员
            if (CollectionUtils.isNotEmpty(rpList)) {
                StringBuffer buffer = new StringBuffer();
                for (com.aier.cloud.basic.api.response.domain.sys.RolePermission rp : rpList) {
                    buffer.append(rp.getRoleName()).append("（角色） ===> ").append(rp.getPermissionName()).append("（操作）</br>");
                }
                BizAssert.empty(rpList, BizException.WARN, "请先解除相应角色与操作的关联：</br>"+buffer);
            }
            sysPermissionService.deleteBatchIds(delPermissions);
        }
        return super.update(sysModule);
    }

    /**
     * 删除模块菜单 是一个非常慎重的操作，会做很多验证
     */
    @Override
    public boolean deleteById(Serializable id) {
        List<SysModule> modules = baseMapper.selectList(new EntityWrapper<SysModule>().eq("parent", id));
        BizAssert.empty(modules, BizException.ERROR,  "删除失败！模块下存在子模块。");
        List<SysPermission> oldPermissions = sysPermissionService.selectList(new EntityWrapper<SysPermission>().eq("module_id", id));
        BizAssert.empty(oldPermissions, BizException.ERROR,  "删除失败！模块下存在操作，请先删除关联操作。");
        return super.deleteById(id);
    }

    @Override
    public SysModule getById(Long id) {
        return baseMapper.getById(id);
    }


    @Override
    public boolean update(SysModule entity) {
        return super.update(entity);
    }
}
