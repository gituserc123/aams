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
@TableName("AuditRecordDetailQuesDesc")
@ApiModel(value = "AuditRecordDetailQuesDesc对象", description = "")
public class AuditRecordDetailQuesDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditRecordDetailQuesDescId", type = IdType.AUTO)
    private Long auditRecordDetailQuesDescId;

    @ApiModelProperty("审计明细ID")
    @TableField("AuditRecordDetailId")
    private Long auditRecordDetailId;

    @ApiModelProperty("问题前缀编码值")
    @TableField("AuditRecordDetailQuesDescPreSuffix")
    private String auditRecordDetailQuesDescPreSuffix;

    @ApiModelProperty("问题后缀编码值")
    @TableField("AuditRecordDetailQuesDescLastSuffix")
    private String auditRecordDetailQuesDescLastSuffix;

    @ApiModelProperty("组合后文本")
    @TableField("AuditRecordDetailQuesDescTxt")
    private String auditRecordDetailQuesDescTxt;

    @TableField("AuditRecordDetailQuesDescCreateTime")
    private Date auditRecordDetailQuesDescCreateTime;


}
