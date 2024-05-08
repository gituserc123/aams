package com.aier.cloud.biz.aams.entity;

import com.aier.cloud.basic.core.base.TreeEntity;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@Accessors(chain = true)
@TableName("T_SYS_ROLE")
@ApiModel(value = "SysRole对象", description = "角色表")
public class SysRole extends TreeEntity<SysRole> implements Serializable {

    private static final long serialVersionUID = 1L;

    /*@ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;*/

    @ApiModelProperty("角色名称")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty("角色类别(1集团角色2医院角色）")
    @TableField("role_type")
    private Byte roleType;

    @ApiModelProperty("角色编码")
    @TableField("role_code")
    private String roleCode;

    @ApiModelProperty("数据范围")
    @TableField("data_scope")
    private Byte dataScope;

    @ApiModelProperty("备注")
    @TableField("remarks")
    private String remarks;

    /*@ApiModelProperty("所属角色")
    @TableField("parent")
    private Long parent;*/

    /*@ApiModelProperty("树路径")
    @TableField("treepath")
    private String treepath;*/

    /*@ApiModelProperty("树层级")
    @TableField("grade")
    private Integer grade;

    @ApiModelProperty("修改人")
    @TableField("modifer")
    private Long modifer;

    @ApiModelProperty("修改时间")
    @TableField("modify_date")
    private Date modifyDate;*/

    /*@ApiModelProperty("排序")
    @TableField("orders")
    private Integer orders;*/

    @ApiModelProperty("平台CODE")
    @TableField("platform_code")
    private String platformCode;

    @ApiModelProperty("角色属性(1 功能角色， 2 报表角色)")
    @TableField("role_attr")
    private Boolean roleAttr;

    @TableField(exist=false)
    private SysRole parent;

    @Override
    public void setParent(SysRole parent) {
        this.parent = parent;
    }

    @Override
    public SysRole getParent() {
        return parent;
    }


}
