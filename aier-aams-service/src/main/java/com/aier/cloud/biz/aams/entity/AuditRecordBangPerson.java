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
@TableName("AuditRecordBangPerson")
@ApiModel(value = "AuditRecordBangPerson对象", description = "")
public class AuditRecordBangPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditRecordBangPersonId", type = IdType.AUTO)
    private Long auditRecordBangPersonId;

    @TableField("AuditRecordId")
    private Long auditRecordId;

    @TableField("AuditRecordBangPersonCreate_time")
    private Date auditrecordbangpersoncreateTime;

    @TableField("AuditRecordBangPersonCreate_user")
    private String auditrecordbangpersoncreateUser;

    @TableField("AuditRecordBangPersonIs_sent")
    private Integer auditrecordbangpersonisSent;

    @TableField("AuditRecordBangPersonOrgMasterId")
    private String auditRecordBangPersonOrgMasterId;

    @TableField("AuditRecordBangPersonUserMainCode")
    private String auditRecordBangPersonUserMainCode;

    @TableField("AuditRecordBangPersonUserCode")
    private String auditRecordBangPersonUserCode;

    @TableField("AuditRecordBangPersonUserName")
    private String auditRecordBangPersonUserName;

    @TableField("AuditRecordBangPersonUserId")
    private Long auditRecordBangPersonUserId;

    @TableField("AuditRecordBangPersonRecordType")
    private String auditRecordBangPersonRecordType;
}
