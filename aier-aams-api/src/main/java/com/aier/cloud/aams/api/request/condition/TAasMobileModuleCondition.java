package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class TAasMobileModuleCondition extends PageCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer menuId;
    private Integer menuType;
    private String menuName;
    private String menuIcon;
    private Integer hidden;
    @Getter(AccessLevel.NONE)
    private Integer sort;
    private String uri;
    private String msg;
    private String path;
    private String name;
    private String component;
    private Integer keepAlive;
    private Long functionId;
    private String functionKey;
}
