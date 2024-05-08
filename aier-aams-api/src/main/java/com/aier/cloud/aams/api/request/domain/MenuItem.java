package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
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
public class MenuItem extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private Integer menuItemId;

    private String menuItemCaption;

    private Integer menuItemOrder;

    private Integer menuItemFatherId;

    private Integer menuItemType;

    private String menuItemLink;

    private String menuItemLinkParameters;

    private String menuItemLinkTarget;

    private String menuItemIconClass;

    private Boolean menuItemShowDeveloperMenuOption;

    private Boolean menuItemShowEditMenuOptions;

    private Long secFunctionalityId;


}
