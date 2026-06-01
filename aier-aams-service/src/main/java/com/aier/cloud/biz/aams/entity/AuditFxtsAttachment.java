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
@TableName("AuditFxtsAttachment")
@ApiModel(value = "AuditFxtsAttachment对象", description = "")
public class AuditFxtsAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditFxtsAttachmentId", type = IdType.AUTO)
    private Long auditFxtsAttachmentId;

    @TableField("AttachmentMasterId")
    private Long attachmentMasterId;

    @TableField("AuditFxtsAttachmentCreate_time")
    private Date auditfxtsattachmentcreateTime;

    @TableField("AuditFxtsAttachmentCreate_user")
    private String auditfxtsattachmentcreateUser;

    @TableField("AuditFxtsAttachmentType")
    private Integer auditFxtsAttachmentType;

    @TableField("AuditRecordId")
    private Long auditRecordId;

    @TableField("AuditFxtsAttachmentReplyId")
    private Long auditFxtsAttachmentReplyId;
}
