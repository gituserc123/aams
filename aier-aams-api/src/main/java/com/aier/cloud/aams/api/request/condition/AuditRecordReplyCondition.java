package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
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
public class AuditRecordReplyCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private String auditRecordReplyID;

    private Long auditRecordId;

    private Integer auditRecordReplyBS;

    private Integer auditRecordReplyStatus;

    private String auditRecordReplyUser;

    private Date auditRecordReplyTime;

    private String auditRecordReplyEvalateUser;

    private Date auditRecordReplyEvalateTime;

    private Date auditRecordReplySQTime;

    private String auditRecordReplySQUser;

    private Date auditRecordReplyMgxxSQTime;

    private String auditRecordReplyMgxxSQUser;

    private Date auditRecordReplyMGxxTime;

    private String auditRecordReplyMgxxUser;

    private Integer auditRecordReplyMgxxStatus;


}
