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
@TableName("RiskQuestionStandard")
@ApiModel(value = "RiskQuestionStandard对象", description = "")
public class RiskQuestionStandard implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "RiskQuestionStandardId", type = IdType.AUTO)
    private Long riskQuestionStandardId;

    @ApiModelProperty("风险点Id")
    @TableField("RiskId")
    private Long riskId;

    @ApiModelProperty("码值")
    @TableField("RiskQuestionStandardCode")
    private Integer riskQuestionStandardCode;

    @ApiModelProperty("问题描述")
    @TableField("RiskQuestionStandardDesc")
    private String riskQuestionStandardDesc;

    @ApiModelProperty("冗余值字段")
    @TableField("RiskQuestionStandardValue")
    private String riskQuestionStandardValue;

    @ApiModelProperty("备注")
    @TableField("RiskQuestionStandardRemark")
    private String riskQuestionStandardRemark;

    @ApiModelProperty("删除标识")
    @TableField("RiskQuestionStandardIsDlt")
    private Boolean riskQuestionStandardIsDlt;

    @TableField("RiskQuestionStandardCreate_user")
    private String riskquestionstandardcreateUser;

    @TableField("RiskQuestionStandardCreate_time")
    private Date riskquestionstandardcreateTime;

    @TableField("RiskQuestionStandardUpdate_User")
    private String riskquestionstandardupdateUser;

    @TableField("RiskQuestionStandardUpdate_time")
    private Date riskquestionstandardupdateTime;


}
