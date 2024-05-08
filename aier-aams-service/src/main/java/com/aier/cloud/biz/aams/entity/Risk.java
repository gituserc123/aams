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
@TableName("Risk")
@ApiModel(value = "Risk对象", description = "")
public class Risk implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "RiskId", type = IdType.AUTO)
    private Long riskId;

    @TableField("RiskName")
    private String riskName;

    @TableField("RiskDesc")
    private String riskDesc;

    @TableField("RiskBussinessType")
    private String riskBussinessType;

    @TableField("RiskProject")
    private String riskProject;

    @TableField("RiskLevel")
    private String riskLevel;

    @TableField("RiskIsdlt")
    private Boolean riskIsdlt;

    @TableField("RiskCreate_time")
    private Date riskcreateTime;

    @TableField("RiskCreate_user")
    private String riskcreateUser;

    @TableField("RiskUpdate_time")
    private Date riskupdateTime;

    @TableField("RiskUpdate_user")
    private String riskupdateUser;

    @TableField("RiskIsPost")
    private Boolean riskIsPost;

    @TableField("RiskMethod")
    private String riskMethod;

    @TableField("RiskInstitution")
    private String riskInstitution;

    @TableField("RiskRemote")
    private Boolean riskRemote;

    @TableField("RiskType")
    private String riskType;

    @TableField("RiskCode")
    private String riskCode;

    @TableField("RiskCapability")
    private Long riskCapability;

    @TableField("RiskSenstivity")
    private Boolean riskSenstivity;

    @TableField("RiskRectifyType")
    private String riskRectifyType;

    @TableField("RiskIsRandom")
    private Boolean riskIsRandom;

    @TableField("SelfRiskId")
    private Long selfRiskId;

    @TableField("RiskSenstivityDesc")
    private String riskSenstivityDesc;

    @TableField("RiskEmergencyRef")
    private String riskEmergencyRef;

    @TableField("RiskRectifyAttribute")
    private String riskRectifyAttribute;

    @TableField("RiskCategory")
    private String riskCategory;

    @TableField("RiskIsQueDescStd")
    private Boolean riskIsQueDescStd;

    @TableField("RiskDigitalModel")
    private String riskDigitalModel;


}
