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

@Getter
@Setter
@Accessors(chain = true)
@TableName("AuditQitaAttachment")
@ApiModel(value = "AuditQitaAttachment对象", description = "")
public class AuditQitaAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditQitaAttachmentId", type = IdType.AUTO)
    private Long auditQitaAttachmentId;

    @TableField("AttachmentMasterId")
    private Long attachmentMasterId;

    @TableField("AuditQitaAttachmentType")
    private Integer auditQitaAttachmentType;

    @TableField("AuditQitaAttachmentCreate_time")
    private Date auditqitaattachmentcreateTime;

    @TableField("AuditQitaAttachmentCreate_user")
    private String auditqitaattachmentcreateUser;

    @TableField("AuditRecordId")
    private Long auditRecordId;

    @TableField("AuditQitaAttachmentReplyId")
    private Long auditQitaAttachmentReplyId;
}
