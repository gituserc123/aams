package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class TAasMobileModule extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer menuId;
    private Integer menuType;
    private String menuName;
    private String menuIcon;
    private Integer hidden;
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
