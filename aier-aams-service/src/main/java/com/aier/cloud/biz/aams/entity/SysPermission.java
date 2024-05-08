package com.aier.cloud.biz.aams.entity;

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
import java.util.Date;

/**
 * <p>
 * 操作表（需要权限管理的页面对象如按钮，图片，文件等）
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("T_SYS_PERMISSION")
@ApiModel(value = "SysPermission对象", description = "操作表（需要权限管理的页面对象如按钮，图片，文件等）")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("操作名称")
    @TableField("perm_name")
    private String permName;

    @ApiModelProperty("操作代码（在shiro中定义的权限代码）")
    @TableField("perm_code")
    private String permCode;

    @ApiModelProperty("所属模块ID")
    @TableField("module_id")
    private Long moduleId;

    @ApiModelProperty("备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty("创建时间")
    @TableField("create_date")
    private Date createDate;

    @ApiModelProperty("修改人")
    @TableField("modifer")
    private Long modifer;

    @ApiModelProperty("修改时间")
    @TableField("modify_date")
    private Date modifyDate;

    @ApiModelProperty("排序号")
    @TableField("orders")
    private Integer orders;

    /** 所属模块sn编码 */
    @TableField(exist=false)
    private String moduleCode;


}
