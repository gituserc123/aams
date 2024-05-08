package com.aier.cloud.biz.aams.service;

import com.aier.cloud.basic.core.base.service.ITreeService;
import com.aier.cloud.biz.aams.entity.SysModule;

import java.util.List;

/**
 * <p>
 * 资源模块表 服务类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
public interface SysModuleService extends ITreeService<SysModule> {

    /**
     * 根据id查询菜单树
     * @param id
     * @return
     */
    List<SysModule> getMenuForTree(Long id);

    /**
     * 查询所有菜单列表
     * @param platformCode
     * @return
     */
    List<SysModule> getAllList(String platformCode);

    /**
     * 根据id查询菜单, 会关联查询父菜单id和名称
     * @param id
     * @return SysModule
     */
    SysModule getById(Long id);
}
