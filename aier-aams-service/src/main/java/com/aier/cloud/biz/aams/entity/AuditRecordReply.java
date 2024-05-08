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
@TableName("AuditRecordReply")
@ApiModel(value = "AuditRecordReply对象", description = "")
public class AuditRecordReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditRecordReplyID", type = IdType.AUTO)
    private String auditRecordReplyID;

    @TableField("AuditRecordId")
    private Long auditRecordId;

    @TableField("AuditRecordReplyBS")
    private Integer auditRecordReplyBS;

    @TableField("AuditRecordReplyStatus")
    private Integer auditRecordReplyStatus;

    @TableField("AuditRecordReplyUser")
    private String auditRecordReplyUser;

    @TableField("AuditRecordReplyTime")
    private Date auditRecordReplyTime;

    @TableField("AuditRecordReplyEvalateUser")
    private String auditRecordReplyEvalateUser;

    @TableField("AuditRecordReplyEvalateTime")
    private Date auditRecordReplyEvalateTime;

    @TableField("AuditRecordReplySQTime")
    private Date auditRecordReplySQTime;

    @TableField("AuditRecordReplySQUser")
    private String auditRecordReplySQUser;

    @TableField("AuditRecordReplyMgxxSQTime")
    private Date auditRecordReplyMgxxSQTime;

    @TableField("AuditRecordReplyMgxxSQUser")
    private String auditRecordReplyMgxxSQUser;

    @TableField("AuditRecordReplyMGxxTime")
    private Date auditRecordReplyMGxxTime;

    @TableField("AuditRecordReplyMgxxUser")
    private String auditRecordReplyMgxxUser;

    @TableField("AuditRecordReplyMgxxStatus")
    private Integer auditRecordReplyMgxxStatus;


}
