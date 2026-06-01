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
@TableName("AuditRecordTypeAuth")
@ApiModel(value = "AuditRecordTypeAuth对象", description = "")
public class AuditRecordTypeAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditRecordTypeAuthId", type = IdType.AUTO)
    private Long auditRecordTypeAuthId;

    @TableField("AuditRecordTypeAuthAuditRecordType")
    private String auditRecordTypeAuthAuditRecordType;

    @TableField("AuditRecordTypeAuthCode")
    private String auditRecordTypeAuthCode;

    @TableField("AuditRecordTypeAuthUserId")
    private Long auditRecordTypeAuthUserId;

    @TableField("AuditRecordTypeAuthUserCode")
    private String auditRecordTypeAuthUserCode;

    @TableField("AuditRecordTypeAuthUserName")
    private String auditRecordTypeAuthUserName;

    @TableField("AuditRecordTypeAuthCreate_time")
    private Date auditrecordtypeauthcreateTime;

    @TableField("AuditRecordTypeAuthCreate_user")
    private String auditrecordtypeauthcreateUser;
}
