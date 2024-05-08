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
@TableName("AuditRecordPlateReply")
@ApiModel(value = "AuditRecordPlateReply对象", description = "")
public class AuditRecordPlateReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditRecordPlateReplyID", type = IdType.AUTO)
    private String auditRecordPlateReplyID;

    @TableField("AuditRecordId")
    private Long auditRecordId;

    @TableField("CodeMasterTypeCode1")
    private String codeMasterTypeCode1;

    @TableField("AuditRecordPlateReplyStatus")
    private Integer auditRecordPlateReplyStatus;

    @TableField("AuditRecordPlateReplyUser")
    private String auditRecordPlateReplyUser;

    @TableField("AuditRecordPlateReplyTime")
    private Date auditRecordPlateReplyTime;

    @TableField("AuditRecordPlateReplyEvalateUser")
    private String auditRecordPlateReplyEvalateUser;

    @TableField("AuditRecordPlateReplyEvalateTime")
    private Date auditRecordPlateReplyEvalateTime;

    @TableField("AuditRecordPlateReplyEvalateXx")
    private String auditRecordPlateReplyEvalateXx;

    @TableField("AuditRecordPlateReplySQTime")
    private Date auditRecordPlateReplySQTime;

    @TableField("AuditRecordPlateReplySQUser")
    private String auditRecordPlateReplySQUser;


}
