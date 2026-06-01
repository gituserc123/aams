package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class AuditQitaAttachment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long auditQitaAttachmentId;
    private Long attachmentMasterId;
    private Integer auditQitaAttachmentType;
    private Date auditqitaattachmentcreateTime;
    private String auditqitaattachmentcreateUser;
    private Long auditRecordId;
    private Long auditQitaAttachmentReplyId;
}
