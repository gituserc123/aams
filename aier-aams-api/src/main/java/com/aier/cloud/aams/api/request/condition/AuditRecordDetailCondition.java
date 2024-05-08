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
public class AuditRecordDetailCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long auditRecordDetailId;

    private Long auditRecordId;

    private Long riskId;

    private String auditRecordDetailStatus;

    private String auditRecordDetailQuestionDesc;

    private String auditrecorddetailcreateUser;

    private String auditrecorddetailupdateUser;

    private Date auditrecorddetailcreateTime;

    private Date auditrecorddetailupdateTime;

    private String auditRecordDetailEmergency;

    private Boolean auditRecordDetailAddToAuditReport;

    private Boolean auditRecordDetailAddToRiskRememberLetter;

    private String auditRecordDetailAuditFind;

    private String auditRecordDetailMarks;

    private Integer auditRecordDetailReplyStatus;

    private String auditRecordDetailManuscript;

    private Integer auditRecordDetailEvaluateDay;

    private String auditRecordDetailRectifyType;

    private String auditRecordDetailRiskCode;

    private String auditRecordDetailJtznzxyj;

    private Date auditrecorddetailjtznzxyjupdateTime;

    private String auditrecorddetailjtznzxyjupdateUser;

    private Date auditrecorddetailauditTime;

    private String auditrecorddetailauditUser;

    private String auditRecordDetailLastAuditFind;

    private Integer auditRecordDetailScore;

    private String auditRecordDetailJtznzxyjStatus;

    private Integer auditRecordDetailScoreCode;

    private Boolean auditRecordDetailIsQueDescStd;

    private String auditRecordDetailHistoryAuditFind;

    private Integer auditRecordDetailRiskScore;

    private Boolean auditRecordDetailSQKeyIssuse;

    private String auditrecorddetailmarksUser;

    private Date auditrecorddetailmarksTime;


}
