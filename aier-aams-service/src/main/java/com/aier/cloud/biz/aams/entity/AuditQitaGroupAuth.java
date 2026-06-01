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
@TableName("AuditQitaGroupAuth")
@ApiModel(value = "AuditQitaGroupAuth对象", description = "")
public class AuditQitaGroupAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditQitaGroupAuthId", type = IdType.AUTO)
    private Long auditQitaGroupAuthId;

    @TableField("SecUserId")
    private Long secUserId;

    @TableField("AuditQitaGroupAuthBusiCode")
    private String auditQitaGroupAuthBusiCode;

    @TableField("AuditQitaGroupAuthCreate_user")
    private String auditqitagroupauthcreateUser;

    @TableField("AuditQitaGroupAuthCreate_time")
    private Date auditqitagroupauthcreateTime;

    @TableField("AuditQitaGroupAuthIsDlt")
    private Integer auditQitaGroupAuthIsDlt;

    @TableField("AuditQitaGroupAuthUpdate_user")
    private String auditqitagroupauthupdateUser;

    @TableField("AuditQitaGroupAuthUpdate_time")
    private Date auditqitagroupauthupdateTime;

    @TableField("AuditQitaGroupAuthOrgMasterId")
    private String auditQitaGroupAuthOrgMasterId;
}
