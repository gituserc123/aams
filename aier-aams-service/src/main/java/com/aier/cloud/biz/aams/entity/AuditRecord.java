package com.aier.cloud.biz.aams.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@TableName("AuditRecord")
@ApiModel(value = "AuditRecord对象", description = "")
public class AuditRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditRecordId", type = IdType.AUTO)
    private Long auditRecordId;

    @TableField("AuditRecordOrgCode")
    private String auditRecordOrgCode;

    @TableField("AuditRecordIsFinished")
    private Boolean auditRecordIsFinished;

    @TableField("AuditRecordIsdlt")
    private Boolean auditRecordIsdlt;

    @TableField("AuditRecordCreate_time")
    private Date auditrecordcreateTime;

    @TableField("AuditRecordCreate_user")
    private String auditrecordcreateUser;

    @TableField("AuditRecordType")
    private String auditRecordType;

    @TableField("AuditRecordName")
    private String auditRecordName;

    @TableField("AuditRecordDateTo")
    private Date auditRecordDateTo;

    @TableField("AuditRecordDateFrom")
    private Date auditRecordDateFrom;

    @TableField("AuditRecordFinished_time")
    private Date auditrecordfinishedTime;

    @TableField("AuditrecordNoteAttachmenId")
    private Long auditrecordNoteAttachmenId;

    @TableField("AuditRecordReprotAttachmentid")
    private Long auditRecordReprotAttachmentid;

    @TableField("AuditRecordStatus")
    private Integer auditRecordStatus;

    @TableField("AuditRecordLeaveHospitalDate")
    private Date auditRecordLeaveHospitalDate;

    @TableField("AuditRecordArriveHospitalDate")
    private Date auditRecordArriveHospitalDate;

    @TableField("AuditRecordMonth")
    private Integer auditRecordMonth;

    @TableField("AuditRecordYear")
    private Integer auditRecordYear;

    @TableField("AuditRecordOverview")
    private String auditRecordOverview;

    @TableField("AuditRecordOverviewRemark")
    private String auditRecordOverviewRemark;

    @TableField("AuditRecordUpdate_time")
    private Date auditrecordupdateTime;

    @TableField("AuditRecordUpdate_user")
    private String auditrecordupdateUser;

    @TableField("AuditRecordRejectReason")
    private String auditRecordRejectReason;

    @TableField("AuditRecordReprotConfirmAttachmentid")
    private Long auditRecordReprotConfirmAttachmentid;

    @TableField("AuditRecordLockUser")
    private String auditRecordLockUser;

    @TableField("AuditRecordParentId")
    private Long auditRecordParentId;

    @TableField("AuditRecordReplyEvalateSubmitTime")
    private Date auditRecordReplyEvalateSubmitTime;

    @TableField("AuditRecordReplyEvalateSubmitUser")
    private String auditRecordReplyEvalateSubmitUser;

    @TableField("AuditRecordReplyEvalateXx")
    private String auditRecordReplyEvalateXx;

    @TableField("AuditRecordReplyFinishTime")
    private Date auditRecordReplyFinishTime;

    @TableField("AuditRecordReplyIsFinish")
    private Boolean auditRecordReplyIsFinish;

    @TableField("AuditRecordCapability")
    private Long auditRecordCapability;

    @TableField("orgMasterId")
    private String orgMasterId;

    @TableField("AuditRecordScore")
    private Integer auditRecordScore;

    @TableField("AuditRecordRandomHG")
    private Boolean auditRecordRandomHG;

    @TableField("AuditRecordOpenToHos")
    private Boolean auditRecordOpenToHos;

    @TableField("AuditRecordConfirmDatetime")
    private Date auditRecordConfirmDatetime;

    @TableField("AuditRecordConfirmUser")
    private String auditRecordConfirmUser;

    @TableField("AuditRecordOpenToHosDeadline")
    private Date auditRecordOpenToHosDeadline;

    @TableField("AuditRecordTheme")
    private String auditRecordTheme;

    @TableField("AuditRecordOverviewRemark_user")
    private String auditrecordoverviewremarkUser;

    @TableField("AuditRecordOverviewRemark_time")
    private Date auditrecordoverviewremarkTime;

    @TableField("AuditRecordFHreview_time")
    private Date auditrecordfhreviewTime;

    @TableField("AuditRecordFHreview_user")
    private String auditrecordfhreviewUser;

    @TableField("AuditRecordCGSubmit_time")
    private Date auditrecordcgsubmitTime;

    @TableField("AuditRecordCGSubmit_user")
    private String auditrecordcgsubmitUser;

    @TableField("AuditRecordGTYJSubmit2_time")
    private Date auditrecordgtyjsubmit2Time;

    @TableField("AuditRecordGTYJSubmit2_user")
    private String auditrecordgtyjsubmit2User;

    @TableField("AuditRecordAgreeOrNot_time")
    private Date auditrecordagreeornotTime;

    @TableField("AuditRecordAgreeOrNot_user")
    private String auditrecordagreeornotUser;

    @TableField("AuditRecordPublicFlag")
    private Integer auditRecordPublicFlag;

    @TableField(exist=false)
    private String orgMasterRegion;

    @TableField(exist=false)
    private String orgMasterName;

    @TableField(exist=false)
    private String orgMasterType;

    @TableField(exist=false)
    private String auditRecordCapabilityDesc;

    @TableField(exist=false)
    private String auditPlanPerson;

    @TableField(exist=false)
    private String auditRecordTypeName;


}
