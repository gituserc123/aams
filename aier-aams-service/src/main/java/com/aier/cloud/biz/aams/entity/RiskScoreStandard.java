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
@TableName("RiskScoreStandard")
@ApiModel(value = "RiskScoreStandard对象", description = "")
public class RiskScoreStandard implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "RiskScoreStandardId", type = IdType.AUTO)
    private Long riskScoreStandardId;

    @ApiModelProperty("审计手册Id/自评手册Id")
    @TableField("RiskScoreStandardManualId")
    private Long riskScoreStandardManualId;

    @ApiModelProperty("类型:1-审计手册/2-自评手册")
    @TableField("RiskScoreStandardType")
    private String riskScoreStandardType;

    @ApiModelProperty("分值")
    @TableField("RiskScoreStandardScore")
    private Integer riskScoreStandardScore;

    @ApiModelProperty("扣分描述")
    @TableField("RiskScoreStandardDesc")
    private String riskScoreStandardDesc;

    @ApiModelProperty("码值")
    @TableField("RiskScoreStandardCode")
    private Integer riskScoreStandardCode;

    @ApiModelProperty("上级码值")
    @TableField("RiskScoreStandardParentCode")
    private Integer riskScoreStandardParentCode;

    @ApiModelProperty("冗余值字段")
    @TableField("RiskScoreStandardValue")
    private String riskScoreStandardValue;

    @ApiModelProperty("删除标识")
    @TableField("RiskScoreStandardIsDlt")
    private Boolean riskScoreStandardIsDlt;

    @TableField("RiskScoreStandardCreate_user")
    private String riskscorestandardcreateUser;

    @TableField("RiskScoreStandardCreate_time")
    private Date riskscorestandardcreateTime;

    @TableField("RiskScoreStandardUpdate_User")
    private String riskscorestandardupdateUser;

    @TableField("RiskScoreStandardUpdate_time")
    private Date riskscorestandardupdateTime;


}
