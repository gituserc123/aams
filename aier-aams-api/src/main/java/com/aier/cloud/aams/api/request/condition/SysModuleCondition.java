package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

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
public class SysModuleCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

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
    private Long parent;

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


}
