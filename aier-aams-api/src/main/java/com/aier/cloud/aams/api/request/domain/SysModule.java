package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.TreeEntity;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资源模块表
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
@Getter
@Setter
public class SysModule extends TreeEntity<SysModule> implements Serializable {


    private static final long serialVersionUID = 7888723845147871445L;
    public static final String TREE_PATH_SEPARATOR = ",";
    public static final Integer GRADE_LEVEL_ONE = 1;
    public static final Integer GRADE_LEVEL_TWO = 2;
    public static final Integer GRADE_LEVEL_THREE = 3;
    public static final Integer GRADE_LEVEL_FOUR = 4;


    // ID
    private Long id;

    // 模块名称
    private String moduleName;

    // 层级（1一级菜单2二级菜单3三级菜单）
    private Integer grade;

    // 图标（图标样式字符串）
    private String icon;

    // 排序优先级（同级菜单的顺序）
    private Integer orders;

    // 模块代码（在shiro中定义的权限代码）
    private String moduleCode;

    // 树路径
    private String treepath;

    // 模块菜单URL
    private String url;

    // 父模块
//    private Long parent;

    // 备注
    private String remarks;

    // 修改人
    private Long modifer;

    // 修改时间
    private Date modifyDate;

    // 创建时间
    private Date createDate;

    // 首拼码
    private String firstSpell;

    // 是否外部链接
    private Boolean outurl;

    // 是否显示
    private Boolean display;

    // 平台CODE
    private String platformCode;

    // 菜单关联的机构
    private String insts;

    /** 子菜单 */
    private List<SysModule> children = Lists.newArrayList();

    /** 模块操作集合 */
    private List<SysPermission> permissions = Lists.newArrayList();


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(getId())
                .addValue(moduleName)
                .addValue(parent)
                .toString();
    }

}
