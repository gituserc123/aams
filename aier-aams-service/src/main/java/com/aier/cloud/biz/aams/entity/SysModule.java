package com.aier.cloud.biz.aams.entity;

import com.aier.cloud.basic.core.base.TreeEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@TableName("T_SYS_MODULE")
@ApiModel(value = "SysModule对象", description = "资源模块表")
public class SysModule extends TreeEntity<SysModule> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String MODULE_ROOT_CODE = "Root";

    /*@ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;*/

    @ApiModelProperty("模块名称")
    @TableField("module_name")
    private String moduleName;

    /*@ApiModelProperty("层级（1一级菜单2二级菜单3三级菜单）")
    @TableField("grade")
    private Integer grade;*/

    @ApiModelProperty("图标（图标样式字符串）")
    @TableField("icon")
    private String icon;

    /*@ApiModelProperty("排序优先级（同级菜单的顺序）")
    @TableField("orders")
    private Integer orders;*/

    @ApiModelProperty("模块代码（在shiro中定义的权限代码）")
    @TableField("module_code")
    private String moduleCode;

    /*@ApiModelProperty("树路径")
    @TableField("treepath")
    private String treepath;*/

    @ApiModelProperty("模块菜单URL")
    @TableField("url")
    private String url;

    /*@ApiModelProperty("父模块")
    @TableField("parent")
    private Long parent;*/

    @ApiModelProperty("备注")
    @TableField("remarks")
    private String remarks;

    /*@ApiModelProperty("修改人")
    @TableField("modifer")
    private Long modifer;*/

    /*@ApiModelProperty("修改时间")
    @TableField("modify_date")
    private Date modifyDate;*/

    @ApiModelProperty("创建时间")
    @TableField("create_date")
    private Date createDate;

    @ApiModelProperty("首拼码")
    @TableField("first_spell")
    private String firstSpell;

    @ApiModelProperty("是否外部链接")
    @TableField("outurl")
    private Boolean outurl;

    @ApiModelProperty("是否显示")
    @TableField("display")
    private Boolean display;

    @ApiModelProperty("平台CODE")
    @TableField("platform_code")
    private String platformCode;

    @ApiModelProperty("菜单关联的机构")
    @TableField("insts")
    private String insts;

    /** 子菜单 */
    @TableField(exist=false)
    private List<SysModule> children = Lists.newArrayList();

    /** 模块操作集合 */
    @TableField(exist=false)
    private List<SysPermission> permissions = Lists.newArrayList();


    @TableField(exist=false)
    private SysModule parent;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(getId())
                .addValue(moduleName)
                .addValue(parent)
                .toString();
    }


    @Override
    public SysModule getParent() {
        return parent;
    }

    @Override
    public void setParent(SysModule parent) {
        this.parent = parent;
    }

}
