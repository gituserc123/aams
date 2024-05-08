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
import java.math.BigDecimal;
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
@TableName("UserSelfEvaluationDetail")
@ApiModel(value = "UserSelfEvaluationDetail对象", description = "")
public class UserSelfEvaluationDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "UserSelfEvaluationDetailId", type = IdType.AUTO)
    private Long userSelfEvaluationDetailId;

    @TableField("UserSelfEvaluationDetailCreate_time")
    private Date userselfevaluationdetailcreateTime;

    @TableField("UserSelfEvaluationDetailCreate_user")
    private String userselfevaluationdetailcreateUser;

    @TableField("UserSelfEvaluationDetailUpdate_time")
    private Date userselfevaluationdetailupdateTime;

    @TableField("UserSelfEvaluationDetailUpdate_user")
    private String userselfevaluationdetailupdateUser;

    @TableField("UserSelfEvaluationDetailScore")
    private BigDecimal userSelfEvaluationDetailScore;

    @TableField("UserSelfEvaluationDetailNonScoreDesc")
    private String userSelfEvaluationDetailNonScoreDesc;

    @TableField("UserSelfEvaluationId")
    private Long userSelfEvaluationId;

    @TableField("UserSelfRiskId")
    private Long userSelfRiskId;

    @TableField("UserSelfEvaluationDetailMeasures")
    private String userSelfEvaluationDetailMeasures;

    @TableField("UserSelfEvaluationDetailResult")
    private String userSelfEvaluationDetailResult;


}
