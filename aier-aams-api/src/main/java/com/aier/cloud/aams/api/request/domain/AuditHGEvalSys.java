package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

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
public class AuditHGEvalSys extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private Long auditHGEvalSysId;

    // 编码
    private String auditHGEvalSysCode;

    // 名称描述
    private String auditHGEvalSysName;

    // 类型
    private String auditHGEvalSysType;

    // 最高分
    private Integer auditHGEvalSysMaxScore;

    // 父级编码
    private String auditHGEvalSysParentCode;

    // 所在层级
    private Integer auditHGEvalSysLevel;

    // 是否删除
    private Boolean auditHGEvalSysIsDlt;

    // 备注/描述
    private String auditHGEvalSysDesc;

    // 筛选条件
    private String auditHGEvalSysFilter;

    private String audithgevalsyscreateUser;

    private Date audithgevalsyscreateTime;

    private String audithgevalsysupdateUser;

    private Date audithgevalsysupdateTime;


}
