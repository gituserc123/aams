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
@TableName("AuditRecordAuditRecordSuggestion")
@ApiModel(value = "AuditRecordAuditRecordSuggestion对象", description = "")
public class AuditRecordAuditRecordSuggestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditRecordId", type = IdType.AUTO)
    private Long auditRecordId;

    @TableId(value = "AuditRecordSuggestionGuid", type = IdType.AUTO)
    private String auditRecordSuggestionGuid;

    @TableField("AuditRecordSuggestionTitle")
    private String auditRecordSuggestionTitle;

    @TableField("AuditRecordSuggestionRemark")
    private String auditRecordSuggestionRemark;

    @TableField("AuditRecordSuggestionUpdate_user")
    private String auditrecordsuggestionupdateUser;

    @TableField("AuditRecordSuggestionUpdate_time")
    private Date auditrecordsuggestionupdateTime;

    @TableField("AuditRecordSuggestionIsdlt")
    private Boolean auditRecordSuggestionIsdlt;

    @TableField("AuditRecordSuggestionRemark_user")
    private String auditrecordsuggestionremarkUser;

    @TableField("AuditRecordSuggestionRemark_time")
    private Date auditrecordsuggestionremarkTime;


}
