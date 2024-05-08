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
@TableName("AuditFxtsReply")
@ApiModel(value = "AuditFxtsReply对象", description = "")
public class AuditFxtsReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditFxtsReplyId", type = IdType.AUTO)
    private Long auditFxtsReplyId;

    @TableField("AuditRecordId")
    private Long auditRecordId;

    @TableField("AuditFxtsReplyUser")
    private Long auditFxtsReplyUser;

    @TableField("AuditFxtsReplyText")
    private String auditFxtsReplyText;

    @TableField("AuditFxtsReplyNum")
    private Integer auditFxtsReplyNum;

    @TableField("AuditFxtsReplyCrtTime")
    private Date auditFxtsReplyCrtTime;

    @TableField("AuditFxtsReplyUpdTime")
    private Date auditFxtsReplyUpdTime;


}
