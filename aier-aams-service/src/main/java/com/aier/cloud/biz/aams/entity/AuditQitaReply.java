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
@TableName("AuditQitaReply")
@ApiModel(value = "AuditQitaReply对象", description = "")
public class AuditQitaReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditQitaReplyId", type = IdType.AUTO)
    private Long auditQitaReplyId;

    @TableField("AuditRecordId")
    private Long auditRecordId;

    @TableField("AuditQitaReplyUser")
    private Long auditQitaReplyUser;

    @TableField("AuditQitaReplyText")
    private String auditQitaReplyText;

    @TableField("AuditQitaReplyNum")
    private Integer auditQitaReplyNum;

    @TableField("AuditQitaReplyCrtTime")
    private Date auditQitaReplyCrtTime;

    @TableField("AuditQitaReplyUpdTime")
    private Date auditQitaReplyUpdTime;
}
