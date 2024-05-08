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
@TableName("AuditRecordBussinessType")
@ApiModel(value = "AuditRecordBussinessType对象", description = "")
public class AuditRecordBussinessType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditRecordBussinessTypeId", type = IdType.AUTO)
    private Long auditRecordBussinessTypeId;

    @TableField("AuditRecordId")
    private Long auditRecordId;

    @TableField("AuditRecordBussinessTypeSuggestion")
    private String auditRecordBussinessTypeSuggestion;

    @TableField("AuditRecordBussinessTypeIsdlt")
    private Boolean auditRecordBussinessTypeIsdlt;

    @TableField("AuditRecordBussinessTypeCreate_user")
    private String auditrecordbussinesstypecreateUser;

    @TableField("AuditRecordBussinessTypeUpdate_user")
    private String auditrecordbussinesstypeupdateUser;

    @TableField("AuditRecordBussinessTypeCreate_time")
    private Date auditrecordbussinesstypecreateTime;

    @TableField("AuditRecordBussinessTypeUpdate_time")
    private Date auditrecordbussinesstypeupdateTime;

    @TableField("AuditRecordBussinessTypeCode")
    private String auditRecordBussinessTypeCode;

    @TableField("AuditRecordBussinessTypeAttachmentId")
    private Long auditRecordBussinessTypeAttachmentId;

    @TableField("AuditRecordBussinessTypeRemarks")
    private String auditRecordBussinessTypeRemarks;

    @TableField("AuditRecordBussinessTypeScore")
    private Integer auditRecordBussinessTypeScore;

    @TableField("AuditRecordBussinessTypeConfirmTime")
    private Date auditRecordBussinessTypeConfirmTime;

    @TableField("AuditRecordBussinessTypeConfirmUser")
    private String auditRecordBussinessTypeConfirmUser;

    @TableField("AuditRecordBussinessTypeOpenToHos")
    private Boolean auditRecordBussinessTypeOpenToHos;

    @TableField("AuditRecordBussinessTypeOpenToHosDeadlin")
    private Date auditRecordBussinessTypeOpenToHosDeadlin;

    @TableField("AuditRecordBussinessTypeRemarks_user")
    private String auditrecordbussinesstyperemarksUser;

    @TableField("AuditRecordBussinessTypeRemarks_time")
    private Date auditrecordbussinesstyperemarksTime;


}
