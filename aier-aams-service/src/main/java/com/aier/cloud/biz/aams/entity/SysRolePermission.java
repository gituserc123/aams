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
 * 角色操作资源表 (角色与操作菜单，按钮的关联关系)
 * </p>
 *
 * @author 柯南
 * @since 2024-04-22 04:55:16
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("T_SYS_ROLE_PERMISSION")
@ApiModel(value = "SysRolePermission对象", description = "角色操作资源表 (角色与操作菜单，按钮的关联关系)")
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色ID")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty("操作资源ID")
    @TableField("permission_id")
    private Long permissionId;

    @ApiModelProperty("修改人")
    @TableField("modifer")
    private Long modifer;

    @ApiModelProperty("修改时间")
    @TableField("modify_date")
    private Date modifyDate;


}
