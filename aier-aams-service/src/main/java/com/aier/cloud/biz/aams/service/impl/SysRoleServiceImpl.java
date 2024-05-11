package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.basic.api.request.condition.sys.RoleCondition;
import com.aier.cloud.basic.common.convert.EnumDict;
import com.aier.cloud.basic.common.exception.BizAssert;
import com.aier.cloud.basic.common.exception.BizException;
import com.aier.cloud.basic.core.base.service.impl.TreeServiceImpl;
import com.aier.cloud.basic.starter.service.util.DbUtil;
import com.aier.cloud.basic.starter.service.util.SysUtil;
import com.aier.cloud.biz.aams.dao.SysRoleMapper;
import com.aier.cloud.biz.aams.entity.SysModule;
import com.aier.cloud.biz.aams.entity.SysRole;
import com.aier.cloud.biz.aams.entity.SysRolePermission;
import com.aier.cloud.biz.aams.service.SysModuleService;
import com.aier.cloud.biz.aams.service.SysPermissionService;
import com.aier.cloud.biz.aams.service.SysRolePermissionService;
import com.aier.cloud.biz.aams.service.SysRoleService;
import com.aier.cloud.center.common.context.UserContext;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
@Service
public class SysRoleServiceImpl extends TreeServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    public enum RoleType implements EnumDict {
        /***/
        JT("1", "集团角色"), LC("2", "临床角色"), GL("3", "管理角色");

        String code;

        private RoleType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        String value;

        @Override
        public String getEnumCode() {
            return code;
        }

        @Override
        public String getEnumDesc() {
            return value;
        }
    }

    public enum DataScope implements EnumDict {
        /***/
        DataScope1("0", "全集团"), DataScope2("1", "医院"), DataScope3("3", "科室"), DataScope4("4", "个人");
        String code;

        private DataScope(String code, String value) {
            this.code = code;
            this.value = value;
        }

        String value;

        @Override
        public String getEnumCode() {
            return code;
        }

        @Override
        public String getEnumDesc() {
            return value;
        }
    }


    @Resource
    SysModuleService ms;

    @Resource
    SysPermissionService ps;

    @Resource
    SysRolePermissionService rps;

    @Override
    public List getPermByRoleId(Long roleId, boolean isEdit, String platformCode) {
        platformCode = this.getTruePlatformCode(platformCode);

        // 1.从数据库取全部菜单及其权限基础数据以及该角色拥有的权限,为后续整理做数据准备
        List<Map> modules = getAllModules(platformCode);
        // 有问题
        List<Map> permissions = getAllPermissions(modules);
        List<Map> rolePermissions = getRolePermissions(roleId, permissions);

        // 2.在内存中处理第1步中拿到的数据
        // 把该角色拥有的权限打勾,没有的权限默认不打勾
        setRolePermStatus(platformCode, permissions, rolePermissions);
        // 把权限包整理跟在菜单后面
        List<Map> moduleAndPerm = addPermIntoModule(isEdit, modules, permissions);

        return moduleAndPerm;

    }

    private List<Map> addPermIntoModule(boolean isEdit, List<Map> modules, List<Map> permissions) {
        List<Map> newMo = Lists.newArrayList();
        for(Map m : modules){
            if(Objects.equals(m.get("moduleCode"), SysModule.MODULE_ROOT_CODE)) {
                m.put("state", "closed");
            }
            m.put("pid", m.remove("parent"));
            m.put("name", m.remove("moduleName"));
            String mid = SysUtil.getString(m, "id");

            // 没错, 我大power就是权限包
            List<Map> power = Lists.newArrayList();
            boolean status = false;
            for(Map p : permissions){
                String pMid = SysUtil.getString(p, "moduleId");
                if(SysUtil.equals(pMid, mid)){
                    status = MapUtils.getBooleanValue(p, "status");
                    if(isEdit){
                        power.add(p);
                    } else{
                        if(status){
                            power.add(p);
                        }
                    }
                }
            }

            // 把权限包排序后,添加跟在菜单里面
            power.sort(SysUtil.ORDERS_SORT);
            m.put("power", power);
            if(!isEdit && power.size() > 0){
                newMo.add(m);
            }
        }

        // 修改的情况拿全部数据,因为可能要打勾或取消打勾
        if(isEdit){
            return modules;

            // 查询的情况当然只关心打了勾的
        } else{
            return newMo;
        }
    }

    private void setRolePermStatus(String platformCode, List<Map> permissions, List<Map> rolePermissions) {
        EntityWrapper em;
        em = new EntityWrapper<>();
        em.eq("platform_code", platformCode);
        em.eq("module_code", "Root");
        //根模块
        //Module root = ms.selectOne(em);
        for(Map p : permissions){
            String id = SysUtil.getString(p, "id");
            //if(Objects.equals(id, root.getId().toString())) {
            //根模块的权限默认选中
            //	p.put("status", true);
            //}else{
            p.put("status", false);
            //}
            for(Map r : rolePermissions){
                String rid = SysUtil.getString(r, "permissionId");
                if(SysUtil.equals(rid, id)){
                    p.put("status", true);
                    break;
                }
            }
        }
    }

    private List<Map> getRolePermissions(Long roleId, List<Map> permissions) {
        Set<Long> permissionIds = SysUtil.getDistinctIds(permissions, "id");

        EntityWrapper em;
        em = new EntityWrapper<>();
        em.setSqlSelect(DbUtil.camel("permissionId"));
        em.eq("role_id", roleId);
        DbUtil.in(em, "permission_id", permissionIds);
        List<Map> rolePermissions = rps.selectMaps(em);
        return rolePermissions;
    }

    private List<Map> getAllPermissions(List<Map> modules) {
        List<Long> moduleIds = SysUtil.getIds(modules, "id");
        EntityWrapper em;
        em = new EntityWrapper<>();
        em.setSqlSelect(DbUtil.camel("id", "permName", "permCode", "moduleId", "remarks", "orders"));
        DbUtil.in(em, "module_id", moduleIds);

        List<Map> permissions = ps.selectMaps(em);
        return permissions;
    }

    private List<Map> getAllModules(String platformCode) {
        EntityWrapper em = new EntityWrapper<>();
        em.setSqlSelect(DbUtil.camel("id", "parent", "moduleName", "grade","moduleCode"));
        //em.eq("platform_code", platformCode);
        List<Map> modules = ms.selectMaps(em);
        return modules;
    }

    @Override
    public void updateRolePerm(RoleCondition rc) {
        EntityWrapper<SysRolePermission> re = new EntityWrapper<>();
        re.eq("role_id", rc.getRoleId());
        rps.delete(re);

        List<Long> permIds = rc.getPermIds();
        if(SysUtil.isNotEmpty(permIds)){
            List<SysRolePermission> lr = Lists.newArrayList();
            for(Long p : permIds){
                if(p != null){
                    SysRolePermission rp = new SysRolePermission();
                    rp.setRoleId(rc.getRoleId());
                    rp.setPermissionId(p);
                    rp.setModifer(UserContext.getUserId());
                    lr.add(rp);
                }
            }
            DbUtil.insertBatch(rps, lr);
        }
    }

    @Override
    public void deleteRole(Long roleId) {
        SysRole r = this.selectById(roleId);
        if(r != null){
            EntityWrapper e = new EntityWrapper();
            e.eq("parent", roleId);
            int count = this.selectCount(e);
            BizAssert.isTrue(count == 0, "角色下有子角色，禁止删除。");

            e = new EntityWrapper();
            e.eq("role_id", roleId);
            // count = staffInstRoleMapper.selectCount(e);
            // 暂时设置为0,后面再改 -- modify by chenyuning 20240423
            count = 0;
            BizAssert.isTrue(count == 0, "该角色已分配用户，请先解绑用户角色关联");
            rps.delete(e);

            e = new EntityWrapper();
            this.deleteById(roleId);
        }
    }

    @Override
    public List getForTree(SysRole r) {

        String platformCode = this.getTruePlatformCode(r.getPlatformCode());
        r.setPlatformCode(platformCode);
        if(Objects.equals(r.getId(), -1L)){
            Long rootId = getRootId(r.getPlatformCode());
            r.setId(rootId);
            return this.baseMapper.getForRoleType(r);
        } else{
            return this.baseMapper.getForTree(r);
        }
    }

    private Long getRootId(String platformCode) {
        platformCode = this.getTruePlatformCode(platformCode);
        EntityWrapper w = new EntityWrapper();
        w.eq("platform_code", platformCode);
        w.eq("grade", 0);
        return this.selectOne(w).getId();
    }


    @Override
    public String getTruePlatformCode(String code) {
        return "aams";
    }

    @Override
    public List<Map<String, Object>> selectRoleTreeByStaffInst(Long staffId, Long instId, String platformCode) {
        platformCode = this.getTruePlatformCode(platformCode);
        return baseMapper.selectRoleTreeByStaffInst(staffId, instId, platformCode);
    }

    @Override
    public List<Map<String, Object>> selectRoleTreeByStaffInstHosp(Long staffId, Long instId, Integer roleType, String platformCode) {
        platformCode = this.getTruePlatformCode(platformCode);
        return baseMapper.selectRoleTreeByStaffInstHosp(staffId, instId, roleType, platformCode);
    }

    @Override
    public void updateRole(SysRole role) {
        String platformCode = this.getTruePlatformCode(role.getPlatformCode());
        role.setPlatformCode(platformCode);
        role.setDataScope((byte) 1);
        EntityWrapper ew = new EntityWrapper<>();
        ew.eq("role_name", role.getRoleName());
        ew.eq("platform_code", platformCode);

        SysRole pr = this.selectById(role.getParentId());
        if(pr != null){
            role.setParent(pr);
        }
        if(role.getId() == null){
            int count = this.selectCount(ew);
            if(count > 0){
                throw new BizException("角色名称重复!");
            }
            this.insert(role);
        } else{
            ew.ne("id", role.getId());
            int count = this.selectCount(ew);
            if(count > 0){
                throw new BizException("角色名称重复!");
            }
            this.update(role);
        }
    }



}
