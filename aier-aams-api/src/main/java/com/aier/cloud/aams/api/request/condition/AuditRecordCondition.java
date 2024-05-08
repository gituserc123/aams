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
public class AuditRecordCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long auditRecordId;

    private String auditRecordOrgCode;

    private Boolean auditRecordIsFinished;

    private Boolean auditRecordIsdlt;

    private Date auditrecordcreateTime;

    private String auditrecordcreateUser;

    private String auditRecordType;

    private String auditRecordName;

    private Date auditRecordDateTo;

    private Date auditRecordDateFrom;

    private Date auditrecordfinishedTime;

    private Long auditrecordNoteAttachmenId;

    private Long auditRecordReprotAttachmentid;

    private Integer auditRecordStatus;

    private Date auditRecordLeaveHospitalDate;

    private Date auditRecordArriveHospitalDate;

    private Integer auditRecordMonth;

    private Integer auditRecordYear;

    private String auditRecordOverview;

    private String auditRecordOverviewRemark;

    private Date auditrecordupdateTime;

    private String auditrecordupdateUser;

    private String auditRecordRejectReason;

    private Long auditRecordReprotConfirmAttachmentid;

    private String auditRecordLockUser;

    private Long auditRecordParentId;

    private Date auditRecordReplyEvalateSubmitTime;

    private String auditRecordReplyEvalateSubmitUser;

    private String auditRecordReplyEvalateXx;

    private Date auditRecordReplyFinishTime;

    private Boolean auditRecordReplyIsFinish;

    private Long auditRecordCapability;

    private String orgMasterId;

    private Integer auditRecordScore;

    private Boolean auditRecordRandomHG;

    private Boolean auditRecordOpenToHos;

    private Date auditRecordConfirmDatetime;

    private String auditRecordConfirmUser;

    private Date auditRecordOpenToHosDeadline;

    private String auditRecordTheme;

    private String auditrecordoverviewremarkUser;

    private Date auditrecordoverviewremarkTime;

    private Date auditrecordfhreviewTime;

    private String auditrecordfhreviewUser;

    private Date auditrecordcgsubmitTime;

    private String auditrecordcgsubmitUser;

    private Date auditrecordgtyjsubmit2Time;

    private String auditrecordgtyjsubmit2User;

    private Date auditrecordagreeornotTime;

    private String auditrecordagreeornotUser;

    private Integer auditRecordPublicFlag;


}
