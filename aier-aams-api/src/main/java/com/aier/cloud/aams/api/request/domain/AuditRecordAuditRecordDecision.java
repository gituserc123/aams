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
public class AuditRecordAuditRecordDecision extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private Long auditRecordId;

    private String auditRecordDecisionGuid;

    private String auditRecordDecisionTitle;

    private String auditRecordDecisionRemark;

    private String auditrecorddecisionupdateUser;

    private Date auditrecorddecisionupdateTime;

    private Boolean auditRecordDecisionIsdlt;

    private String auditrecorddecisionremarkUser;

    private Date auditrecorddecisionremarkTime;


}
