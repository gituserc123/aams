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
/*@Getter
@Setter*/
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

    public Long getAuditFxtsReplyId() {
        return auditFxtsReplyId;
    }

    public void setAuditFxtsReplyId(Long auditFxtsReplyId) {
        this.auditFxtsReplyId = auditFxtsReplyId;
    }

    public Long getAuditRecordId() {
        return auditRecordId;
    }

    public void setAuditRecordId(Long auditRecordId) {
        this.auditRecordId = auditRecordId;
    }

    public Long getAuditFxtsReplyUser() {
        return auditFxtsReplyUser;
    }

    public void setAuditFxtsReplyUser(Long auditFxtsReplyUser) {
        this.auditFxtsReplyUser = auditFxtsReplyUser;
    }

    public String getAuditFxtsReplyText() {
        return auditFxtsReplyText;
    }

    public void setAuditFxtsReplyText(String auditFxtsReplyText) {
        this.auditFxtsReplyText = auditFxtsReplyText;
    }

    public Integer getAuditFxtsReplyNum() {
        return auditFxtsReplyNum;
    }

    public void setAuditFxtsReplyNum(Integer auditFxtsReplyNum) {
        this.auditFxtsReplyNum = auditFxtsReplyNum;
    }

    public Date getAuditFxtsReplyCrtTime() {
        return auditFxtsReplyCrtTime;
    }

    public void setAuditFxtsReplyCrtTime(Date auditFxtsReplyCrtTime) {
        this.auditFxtsReplyCrtTime = auditFxtsReplyCrtTime;
    }

    public Date getAuditFxtsReplyUpdTime() {
        return auditFxtsReplyUpdTime;
    }

    public void setAuditFxtsReplyUpdTime(Date auditFxtsReplyUpdTime) {
        this.auditFxtsReplyUpdTime = auditFxtsReplyUpdTime;
    }
}
