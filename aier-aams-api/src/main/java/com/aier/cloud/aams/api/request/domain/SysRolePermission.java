package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
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
public class SysRolePermission extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    // id
    private Long id;

    // 角色ID
    private Long roleId;

    // 操作资源ID
    private Long permissionId;

    // 修改人
    private Long modifer;

    // 修改时间
    private Date modifyDate;


}
