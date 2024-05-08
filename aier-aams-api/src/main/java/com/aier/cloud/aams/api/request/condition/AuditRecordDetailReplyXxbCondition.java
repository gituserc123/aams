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
public class AuditRecordDetailReplyXxbCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private String auditRecordDetailReplyXxbID;

    private String auditRecordDetailReplyID;

    private String auditRecordDetailReplyXxbHfxx;

    private String auditrecorddetailreplyxxbHfuser;

    private Date auditrecorddetailreplyxxbHftime;

    private String auditRecordDetailReplyXxbOverdueReason;

    private Date auditRecordDetailReplyXxbSJFinishTime;

    private Integer auditRecordDetailReplyXxbStatus;

    private Integer auditRecordDetailReplyXxbBS;

    private String auditrecorddetailreplyxxbsqpjUser;

    private Date auditrecorddetailreplyxxbsqpjTime;

    private Integer auditRecordDetailReplyXxbSqpjjg;

    private String auditRecordDetailReplyXxbSqpjxx;

    private Integer auditRecordDetailReplyXxbHFBS;

    private Integer auditRecordDetailReplyXxbXGBS;


}
