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
@TableName("SelfEvaluation")
@ApiModel(value = "SelfEvaluation对象", description = "")
public class SelfEvaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "SelfEvaluationId", type = IdType.AUTO)
    private Long selfEvaluationId;

    @TableField("SelfEvaluationCreate_time")
    private Date selfevaluationcreateTime;

    @TableField("SelfEvaluationCreate_user")
    private String selfevaluationcreateUser;

    @TableField("SelfEvaluationScore")
    private Integer selfEvaluationScore;

    @TableField("SelfEvaluationResultDesc")
    private String selfEvaluationResultDesc;

    @TableField("SelfEvaluationUpdate_user")
    private String selfevaluationupdateUser;

    @TableField("SelfEvaluationUpdate_time")
    private Date selfevaluationupdateTime;

    @TableField("SelfEvaluationFinishedTime")
    private Date selfEvaluationFinishedTime;

    @TableField("SelfEvaluationStatus")
    private Integer selfEvaluationStatus;

    @TableField("orgMasterId")
    private String orgMasterId;

    @TableField("SelfEvaluationIsdlt")
    private Boolean selfEvaluationIsdlt;

    @TableField("SelfEvaluationType")
    private Integer selfEvaluationType;

    @TableField("SelfEvaluationForHospType")
    private Long selfEvaluationForHospType;

    @TableField("SelfEvaluationName")
    private String selfEvaluationName;

    @TableField("SelfEvaluationMonth")
    private Integer selfEvaluationMonth;

    @TableField("SelfEvaluationYear")
    private Integer selfEvaluationYear;

    @TableField("SelfEvaluationSubmitDate")
    private Date selfEvaluationSubmitDate;

    @TableField("SelfEvaluationIsClosed")
    private Boolean selfEvaluationIsClosed;


}
