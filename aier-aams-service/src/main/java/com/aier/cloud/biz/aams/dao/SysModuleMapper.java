package com.aier.cloud.biz.aams.dao;

import com.aier.cloud.biz.aams.entity.SysModule;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 资源模块表 Mapper 接口
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
@Mapper
public interface SysModuleMapper extends BaseMapper<SysModule> {

    /**
     * 根据id查询菜单树
     * @param id
     * @return
     */
    List<SysModule> getForTree(@Param("id") Long id);

    /**
     * 查询所有菜单列表
     * @param platformCode
     * @return
     */
    List<SysModule> getList(@Param("platformCode") String platformCode);

    /**
     * 门户普通用户获取所有菜单
     * @return
     */
    List<SysModule> getListByPortal(@Param("staffId") Long staffId, @Param("instId") Long instId);

    /**
     * 门户获取所有菜单
     * @return
     */
    List<SysModule> getAllListByPortal();

    /**
     * 根据id查询菜单
     * @param id
     * @return SysModule
     */
    SysModule getById(@Param("id") Long id);

    /**
     * 根据机构id 模糊匹配菜单id集合
     * @param instId 机构id
     * @return
     */
    List<Long> getListByInst(@Param("instId") Long instId);
}
