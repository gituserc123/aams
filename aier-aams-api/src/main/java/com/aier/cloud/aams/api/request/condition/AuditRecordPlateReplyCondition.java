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
public class AuditRecordPlateReplyCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private String auditRecordPlateReplyID;

    private Long auditRecordId;

    private String codeMasterTypeCode1;

    private Integer auditRecordPlateReplyStatus;

    private String auditRecordPlateReplyUser;

    private Date auditRecordPlateReplyTime;

    private String auditRecordPlateReplyEvalateUser;

    private Date auditRecordPlateReplyEvalateTime;

    private String auditRecordPlateReplyEvalateXx;

    private Date auditRecordPlateReplySQTime;

    private String auditRecordPlateReplySQUser;


}
