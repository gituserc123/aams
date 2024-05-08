package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.basic.starter.service.util.SysUtil;
import com.aier.cloud.biz.aams.dao.SysPermissionMapper;
import com.aier.cloud.biz.aams.entity.SysPermission;
import com.aier.cloud.biz.aams.service.SysPermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 操作表（需要权限管理的页面对象如按钮，图片，文件等） 服务实现类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {


    @Override
    public List<SysPermission> selectAllList(String platformCode) {
        return baseMapper.selectAllList(platformCode);
    }

    @Override
    public List<SysPermission> selectListByUserAndInst(Long staffId, Long instId, String platformCode) {
        return baseMapper.selectListByUserAndInst(staffId, instId, platformCode);
    }

    @Override
    public List<Map<String, Object>> lookUpAuthorize(Long staffId, Long instId, String platformCode) {

        //1.先查询出模块权限
        List<Map<String,Object>> moduleList = baseMapper.selectModuleMapsByUserAndInst(staffId, instId, platformCode);

        //2.再查询出操作权限
        List<Map<String,Object>> permList = baseMapper.selectMapByUserAndInst(staffId, instId, platformCode);

        //3.聚合数据
        for (Map<String,Object> module : moduleList) {
            List<Map<String,Object>> modPermList = permList.stream().distinct().filter(line -> module.get("id").equals(line.get("moduleid"))).collect(Collectors.toList());
            modPermList.sort(SysUtil.ORDERS_SORT);
            module.put("power", modPermList);
        }

        return moduleList;
    }

    @Override
    public List<String> selectPlatFormListByStaff(Long staffId) {
        return baseMapper.selectPlatFormListByStaff(staffId);
    }
}
