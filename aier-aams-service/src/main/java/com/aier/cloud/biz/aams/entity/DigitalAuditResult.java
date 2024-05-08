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
@TableName("DigitalAuditResult")
@ApiModel(value = "DigitalAuditResult对象", description = "")
public class DigitalAuditResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "DigitalAuditResultId", type = IdType.AUTO)
    private Long digitalAuditResultId;

    @TableField("DigitalAuditResult_AuditRecordId")
    private Long digitalauditresultAuditrecordid;

    @TableField("DigitalAuditResult_RiskId")
    private Long digitalauditresultRiskid;

    @TableField("DigitalAuditResultUpdate_time")
    private Date digitalauditresultupdateTime;

    @TableField("DigitalAuditResultUpdate_user")
    private String digitalauditresultupdateUser;

    @TableField("DigitalAuditResultContent")
    private String digitalAuditResultContent;


}
