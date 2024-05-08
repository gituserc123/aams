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
@TableName("AuditHGEvalSys")
@ApiModel(value = "AuditHGEvalSys对象", description = "")
public class AuditHGEvalSys implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditHGEvalSysId", type = IdType.AUTO)
    private Long auditHGEvalSysId;

    @ApiModelProperty("编码")
    @TableField("AuditHGEvalSysCode")
    private String auditHGEvalSysCode;

    @ApiModelProperty("名称描述")
    @TableField("AuditHGEvalSysName")
    private String auditHGEvalSysName;

    @ApiModelProperty("类型")
    @TableField("AuditHGEvalSysType")
    private String auditHGEvalSysType;

    @ApiModelProperty("最高分")
    @TableField("AuditHGEvalSysMaxScore")
    private Integer auditHGEvalSysMaxScore;

    @ApiModelProperty("父级编码")
    @TableField("AuditHGEvalSysParentCode")
    private String auditHGEvalSysParentCode;

    @ApiModelProperty("所在层级")
    @TableField("AuditHGEvalSysLevel")
    private Integer auditHGEvalSysLevel;

    @ApiModelProperty("是否删除")
    @TableField("AuditHGEvalSysIsDlt")
    private Boolean auditHGEvalSysIsDlt;

    @ApiModelProperty("备注/描述")
    @TableField("AuditHGEvalSysDesc")
    private String auditHGEvalSysDesc;

    @ApiModelProperty("筛选条件")
    @TableField("AuditHGEvalSysFilter")
    private String auditHGEvalSysFilter;

    @TableField("AuditHGEvalSysCreate_user")
    private String audithgevalsyscreateUser;

    @TableField("AuditHGEvalSysCreate_time")
    private Date audithgevalsyscreateTime;

    @TableField("AuditHGEvalSysUpdate_User")
    private String audithgevalsysupdateUser;

    @TableField("AuditHGEvalSysUpdate_time")
    private Date audithgevalsysupdateTime;


}
