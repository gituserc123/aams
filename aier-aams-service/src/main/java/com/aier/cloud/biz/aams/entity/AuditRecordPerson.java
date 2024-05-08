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
@TableName("AuditRecordPerson")
@ApiModel(value = "AuditRecordPerson对象", description = "")
public class AuditRecordPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditRecordPersonId", type = IdType.AUTO)
    private Long auditRecordPersonId;

    @TableField("AuditRecordId")
    private Long auditRecordId;

    @TableField("SecUserId")
    private Long secUserId;

    @TableField("AuditRecordPersonCreate_time")
    private Date auditrecordpersoncreateTime;

    @TableField("AuditRecordPersonCreate_user")
    private String auditrecordpersoncreateUser;

    @TableField("AuditRecordPersonLx")
    private Integer auditRecordPersonLx;

    @TableField("AuditRecordPersonIsManager")
    private Boolean auditRecordPersonIsManager;


}
