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
public class AuditRecordRelaHGSys extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private Long auditRecordRelaHGSysId;

    // AuditHGEvalSys表主键Id
    private Long auditHGEvalSysId;

    // 审计Id
    private Long auditRecordId;

    // 删除标识
    private Boolean auditRecordRelaHGSysIsDlt;

    // 得分
    private Integer auditRecordRelaHGSysScore;

    private String auditrecordrelahgsyscreateUser;

    private Date auditrecordrelahgsyscreateTime;

    private String auditrecordrelahgsysupdateUser;

    private Date auditrecordrelahgsysupdateTime;


}
