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

/**
 * <p>
 * 
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("MenuItem")
@ApiModel(value = "MenuItem对象", description = "")
public class MenuItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "MenuItemId", type = IdType.AUTO)
    private Integer menuItemId;

    @TableField("MenuItemCaption")
    private String menuItemCaption;

    @TableField("MenuItemOrder")
    private Integer menuItemOrder;

    @TableField("MenuItemFatherId")
    private Integer menuItemFatherId;

    @TableField("MenuItemType")
    private Integer menuItemType;

    @TableField("MenuItemLink")
    private String menuItemLink;

    @TableField("MenuItemLinkParameters")
    private String menuItemLinkParameters;

    @TableField("MenuItemLinkTarget")
    private String menuItemLinkTarget;

    @TableField("MenuItemIconClass")
    private String menuItemIconClass;

    @TableField("MenuItemShowDeveloperMenuOption")
    private Boolean menuItemShowDeveloperMenuOption;

    @TableField("MenuItemShowEditMenuOptions")
    private Boolean menuItemShowEditMenuOptions;

    @TableField("SecFunctionalityId")
    private Long secFunctionalityId;


}
