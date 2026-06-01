package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class AuditFxtsAttachment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long auditFxtsAttachmentId;
    private Long attachmentMasterId;
    private Date auditfxtsattachmentcreateTime;
    private String auditfxtsattachmentcreateUser;
    private Integer auditFxtsAttachmentType;
    private Long auditRecordId;
    private Long auditFxtsAttachmentReplyId;
}
