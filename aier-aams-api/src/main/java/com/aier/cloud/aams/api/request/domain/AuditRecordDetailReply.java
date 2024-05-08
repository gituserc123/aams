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
public class AuditRecordDetailReply extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private String auditRecordDetailReplyID;

    private Long auditRecordDetailId;

    private String auditRecordReplyID1;

    private String auditrecorddetailreplyevaluateUser;

    private Date auditrecorddetailreplyevaluateTime;

    private Integer auditRecordDetailReplyEvaluateLx;

    private Integer auditRecordDetailReplyEvaluatYxx;

    private String auditRecordDetailReplyEvaluatXx;

    private Integer auditRecordDetailReplyBS;


}
