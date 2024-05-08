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
@TableName("AuditFxtsRela")
@ApiModel(value = "AuditFxtsRela对象", description = "")
public class AuditFxtsRela implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditFxtsRelaId", type = IdType.AUTO)
    private Long auditFxtsRelaId;

    @TableField("AuditRecordId")
    private Long auditRecordId;

    @TableField("AuditFxtsRelaCode")
    private String auditFxtsRelaCode;

    @TableField("AuditFxtsRelaName")
    private String auditFxtsRelaName;

    @TableField("AuditFxtsRelaType")
    private Integer auditFxtsRelaType;


}
