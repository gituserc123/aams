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
@TableName("AuditRecordAuditRecordDecision")
@ApiModel(value = "AuditRecordAuditRecordDecision对象", description = "")
public class AuditRecordAuditRecordDecision implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditRecordId", type = IdType.AUTO)
    private Long auditRecordId;

    @TableId(value = "AuditRecordDecisionGuid", type = IdType.AUTO)
    private String auditRecordDecisionGuid;

    @TableField("AuditRecordDecisionTitle")
    private String auditRecordDecisionTitle;

    @TableField("AuditRecordDecisionRemark")
    private String auditRecordDecisionRemark;

    @TableField("AuditRecordDecisionUpdate_user")
    private String auditrecorddecisionupdateUser;

    @TableField("AuditRecordDecisionUpdate_time")
    private Date auditrecorddecisionupdateTime;

    @TableField("AuditRecordDecisionIsdlt")
    private Boolean auditRecordDecisionIsdlt;

    @TableField("AuditRecordDecisionRemark_user")
    private String auditrecorddecisionremarkUser;

    @TableField("AuditRecordDecisionRemark_time")
    private Date auditrecorddecisionremarkTime;


}
