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
@TableName("SelfEvaluationDetail")
@ApiModel(value = "SelfEvaluationDetail对象", description = "")
public class SelfEvaluationDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "SelfEvaluationDetailId", type = IdType.AUTO)
    private Long selfEvaluationDetailId;

    @TableField("SelfEvaluationId")
    private Long selfEvaluationId;

    @TableField("SelfRiskId")
    private Long selfRiskId;

    @TableField("SelfEvaluationDetailResult")
    private String selfEvaluationDetailResult;

    @TableField("SelfEvaluationDetailIssueDesc")
    private String selfEvaluationDetailIssueDesc;

    @TableField("SelfEvaluationDetailMeasures")
    private String selfEvaluationDetailMeasures;

    @TableField("SelfEvaluationDetailSubmit_time")
    private Date selfevaluationdetailsubmitTime;

    @TableField("SelfEvaluationDetailCreate_time")
    private Date selfevaluationdetailcreateTime;

    @TableField("SelfEvaluationDetailCreate_user")
    private String selfevaluationdetailcreateUser;

    @TableField("SelfEvaluationDetailUpdate_time")
    private Date selfevaluationdetailupdateTime;

    @TableField("SelfEvaluationDetailUpdate_user")
    private String selfevaluationdetailupdateUser;

    @TableField("SelfEvaluationDetailScore")
    private Integer selfEvaluationDetailScore;

    @TableField("SelfEvaluationDetailScoreCode")
    private Integer selfEvaluationDetailScoreCode;


}
