package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class AuditRecordTypeAuth extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long auditRecordTypeAuthId;
    private String auditRecordTypeAuthAuditRecordType;
    private String auditRecordTypeAuthCode;
    private Long auditRecordTypeAuthUserId;
    private String auditRecordTypeAuthUserCode;
    private String auditRecordTypeAuthUserName;
    private Date auditrecordtypeauthcreateTime;
    private String auditrecordtypeauthcreateUser;
}
