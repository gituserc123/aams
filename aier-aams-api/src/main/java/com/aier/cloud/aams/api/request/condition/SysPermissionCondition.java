package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
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
public class SysPermissionCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    // id
    private Long id;

    // 操作名称
    private String permName;

    // 操作代码（在shiro中定义的权限代码）
    private String permCode;

    // 所属模块ID
    private Long moduleId;

    // 备注
    private String remarks;

    // 创建时间
    private Date createDate;

    // 修改人
    private Long modifer;

    // 修改时间
    private Date modifyDate;

    // 排序号
    private Integer orders;


}
