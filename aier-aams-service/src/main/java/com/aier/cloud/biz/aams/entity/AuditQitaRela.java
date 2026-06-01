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

@Getter
@Setter
@Accessors(chain = true)
@TableName("AuditQitaRela")
@ApiModel(value = "AuditQitaRela对象", description = "")
public class AuditQitaRela implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditQitaRelaId", type = IdType.AUTO)
    private Long auditQitaRelaId;

    @TableField("AuditRecordId")
    private Long auditRecordId;

    @TableField("AuditQitaRelaCode")
    private String auditQitaRelaCode;

    @TableField("AuditQitaRelaName")
    private String auditQitaRelaName;

    @TableField("AuditQitaRelaType")
    private Integer auditQitaRelaType;
}
