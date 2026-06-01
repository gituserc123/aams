package com.aier.cloud.biz.aams.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@TableName("t_aas_mobile_module")
@ApiModel(value = "TAasMobileModule对象", description = "")
public class TAasMobileModule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menuId", type = IdType.AUTO)
    private Integer menuId;

    @TableField("menuType")
    private Integer menuType;

    @TableField("menuName")
    private String menuName;

    @TableField("menuIcon")
    private String menuIcon;

    @TableField("hidden")
    private Integer hidden;

    @TableField("sort")
    private Integer sort;

    @TableField("uri")
    private String uri;

    @TableField("msg")
    private String msg;

    @TableField("path")
    private String path;

    @TableField("name")
    private String name;

    @TableField("component")
    private String component;

    @TableField("keepAlive")
    private Integer keepAlive;

    @TableField("functionId")
    private Long functionId;

    @TableField("functionKey")
    private String functionKey;
}
