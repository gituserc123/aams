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
@TableName("AuditRecordDetail")
@ApiModel(value = "AuditRecordDetail对象", description = "")
public class AuditRecordDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditRecordDetailId", type = IdType.AUTO)
    private Long auditRecordDetailId;

    @TableField("AuditRecordId")
    private Long auditRecordId;

    @TableField("RiskId")
    private Long riskId;

    @TableField("AuditRecordDetailStatus")
    private String auditRecordDetailStatus;

    @TableField("AuditRecordDetailQuestionDesc")
    private String auditRecordDetailQuestionDesc;

    @TableField("AuditRecordDetailCreate_user")
    private String auditrecorddetailcreateUser;

    @TableField("AuditRecordDetailUpdate_user")
    private String auditrecorddetailupdateUser;

    @TableField("AuditRecordDetailCreate_time")
    private Date auditrecorddetailcreateTime;

    @TableField("AuditRecordDetailUpdate_time")
    private Date auditrecorddetailupdateTime;

    @TableField("AuditRecordDetailEmergency")
    private String auditRecordDetailEmergency;

    @TableField("AuditRecordDetailAddToAuditReport")
    private Boolean auditRecordDetailAddToAuditReport;

    @TableField("AuditRecordDetailAddToRiskRememberLetter")
    private Boolean auditRecordDetailAddToRiskRememberLetter;

    @TableField("AuditRecordDetailAuditFind")
    private String auditRecordDetailAuditFind;

    @TableField("AuditRecordDetailMarks")
    private String auditRecordDetailMarks;

    @TableField("AuditRecordDetailReplyStatus")
    private Integer auditRecordDetailReplyStatus;

    @TableField("AuditRecordDetailManuscript")
    private String auditRecordDetailManuscript;

    @TableField("AuditRecordDetailEvaluateDay")
    private Integer auditRecordDetailEvaluateDay;

    @TableField("AuditRecordDetailRectifyType")
    private String auditRecordDetailRectifyType;

    @TableField("AuditRecordDetailRiskCode")
    private String auditRecordDetailRiskCode;

    @TableField("AuditRecordDetailJtznzxyj")
    private String auditRecordDetailJtznzxyj;

    @TableField("AuditRecordDetailjtznzxyjUpdate_time")
    private Date auditrecorddetailjtznzxyjupdateTime;

    @TableField("AuditRecordDetailjtznzxyjUpdate_user")
    private String auditrecorddetailjtznzxyjupdateUser;

    @TableField("AuditRecordDetailAudit_time")
    private Date auditrecorddetailauditTime;

    @TableField("AuditRecordDetailAudit_user")
    private String auditrecorddetailauditUser;

    @TableField("AuditRecordDetailLastAuditFind")
    private String auditRecordDetailLastAuditFind;

    @TableField("AuditRecordDetailScore")
    private Integer auditRecordDetailScore;

    @TableField("AuditRecordDetailJtznzxyjStatus")
    private String auditRecordDetailJtznzxyjStatus;

    @TableField("AuditRecordDetailScoreCode")
    private Integer auditRecordDetailScoreCode;

    @TableField("AuditRecordDetailIsQueDescStd")
    private Boolean auditRecordDetailIsQueDescStd;

    @TableField("AuditRecordDetailHistoryAuditFind")
    private String auditRecordDetailHistoryAuditFind;

    @TableField("AuditRecordDetailRiskScore")
    private Integer auditRecordDetailRiskScore;

    @TableField("AuditRecordDetailSQKeyIssuse")
    private Boolean auditRecordDetailSQKeyIssuse;

    @TableField("AuditRecordDetailMarks_user")
    private String auditrecorddetailmarksUser;

    @TableField("AuditRecordDetailMarks_time")
    private Date auditrecorddetailmarksTime;


}
