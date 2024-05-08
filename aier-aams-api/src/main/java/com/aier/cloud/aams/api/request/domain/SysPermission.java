package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
import com.google.common.base.Objects;
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
public class SysPermission extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;


    public final static String PERMISSION_CREATE = "save";

    public final static String PERMISSION_READ = "view";

    public final static String PERMISSION_UPDATE = "edit";

    public final static String PERMISSION_DELETE = "delete";

    public final static String PERMISSION_COLON = ":";

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

    /** 所属模块sn编码 */
    private String moduleCode;


    /**
     * @param
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj instanceof SysPermission) {
            SysPermission that = (SysPermission) obj;
            return Objects.equal(getId(), that.getId())
                    && Objects.equal(getPermName(), that.getPermName())
                    && Objects.equal(getPermCode(), that.getPermCode());
        }

        return false;
    }

    /**
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getPermName());
    }


}
