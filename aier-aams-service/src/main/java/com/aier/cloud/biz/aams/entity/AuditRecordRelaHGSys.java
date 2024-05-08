package com.aier.cloud.biz.aams.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("AuditRecordRelaHGSys")
@ApiModel(value = "AuditRecordRelaHGSys对象", description = "")
public class AuditRecordRelaHGSys implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditRecordRelaHGSysId", type = IdType.AUTO)
    private Long auditRecordRelaHGSysId;

    @ApiModelProperty("AuditHGEvalSys表主键Id")
    @TableField("AuditHGEvalSysId")
    private Long auditHGEvalSysId;

    @ApiModelProperty("审计Id")
    @TableField("AuditRecordId")
    private Long auditRecordId;

    @ApiModelProperty("删除标识")
    @TableField("AuditRecordRelaHGSysIsDlt")
    private Boolean auditRecordRelaHGSysIsDlt;

    @ApiModelProperty("得分")
    @TableField("AuditRecordRelaHGSysScore")
    private Integer auditRecordRelaHGSysScore;

    @TableField("AuditRecordRelaHGSysCreate_user")
    private String auditrecordrelahgsyscreateUser;

    @TableField("AuditRecordRelaHGSysCreate_time")
    private Date auditrecordrelahgsyscreateTime;

    @TableField("AuditRecordRelaHGSysUpdate_User")
    private String auditrecordrelahgsysupdateUser;

    @TableField("AuditRecordRelaHGSysUpdate_time")
    private Date auditrecordrelahgsysupdateTime;


}
