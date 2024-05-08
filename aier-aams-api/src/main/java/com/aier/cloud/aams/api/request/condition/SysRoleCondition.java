package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
@Getter
@Setter
public class SysRoleCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    // id
    private Long id;

    // 角色名称
    private String roleName;

    // 角色类别(1集团角色2医院角色）
    private Boolean roleType;

    // 角色编码
    private String roleCode;

    // 数据范围
    private Boolean dataScope;

    // 备注
    private String remarks;

    // 所属角色
    private Long parent;

    // 树路径
    private String treepath;

    // 树层级
    private Integer grade;

    // 修改人
    private Long modifer;

    // 修改时间
    private Date modifyDate;

    // 排序
    private Integer orders;

    // 平台CODE
    private String platformCode;

    // 角色属性(1 功能角色， 2 报表角色)
    private Boolean roleAttr;


}
