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
@TableName("SelfRisk")
@ApiModel(value = "SelfRisk对象", description = "")
public class SelfRisk implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "SelfRiskId", type = IdType.AUTO)
    private Long selfRiskId;

    @TableField("SelfRiskCode")
    private String selfRiskCode;

    @TableField("SelfRiskTitle")
    private String selfRiskTitle;

    @TableField("SelfRiskDesc")
    private String selfRiskDesc;

    @TableField("SelfRiskBussinessType")
    private String selfRiskBussinessType;

    @TableField("SelfRiskFirstLevel")
    private String selfRiskFirstLevel;

    @TableField("SelfRiskSecondLevel")
    private String selfRiskSecondLevel;

    @TableField("SelfRiskEvalucationBasis")
    private String selfRiskEvalucationBasis;

    @TableField("SelfRiskMethod")
    private String selfRiskMethod;

    @TableField("SelfRiskGuide")
    private String selfRiskGuide;

    @TableField("SelfRiskType")
    private String selfRiskType;

    @TableField("SelfRiskIsdlt")
    private Boolean selfRiskIsdlt;

    @TableField("SelfRiskCreate_time")
    private Date selfriskcreateTime;

    @TableField("SelfRiskCreate_user")
    private String selfriskcreateUser;

    @TableField("SelfRiskUpdate_time")
    private Date selfriskupdateTime;

    @TableField("SelfRiskUpdate_user")
    private String selfriskupdateUser;

    @TableField("SelfRiskForHospType")
    private Long selfRiskForHospType;

    @TableField("SelfRiskDeductCriterion")
    private String selfRiskDeductCriterion;

    @TableField("SelfRiskScore")
    private Integer selfRiskScore;


}
