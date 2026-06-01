package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class AuditQitaReply extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long auditQitaReplyId;
    private Long auditRecordId;
    private Long auditQitaReplyUser;
    private String auditQitaReplyText;
    private Integer auditQitaReplyNum;
    private Date auditQitaReplyCrtTime;
    private Date auditQitaReplyUpdTime;
}
