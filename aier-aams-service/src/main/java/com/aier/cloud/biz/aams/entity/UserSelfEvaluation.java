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
@TableName("UserSelfEvaluation")
@ApiModel(value = "UserSelfEvaluation对象", description = "")
public class UserSelfEvaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "UserSelfEvaluationId", type = IdType.AUTO)
    private Long userSelfEvaluationId;

    @TableField("UserSelfEvaluationCreate_time")
    private Date userselfevaluationcreateTime;

    @TableField("UserSelfEvaluationCreate_User")
    private String userselfevaluationcreateUser;

    @TableField("UserSelfEvaluationUpdate_time")
    private Date userselfevaluationupdateTime;

    @TableField("UserSelfEvaluationUpdate_user")
    private String userselfevaluationupdateUser;

    @TableField("UserSelfEvaluationIsdlt")
    private Boolean userSelfEvaluationIsdlt;

    @TableField("UserSelfEvaluationDeptCode")
    private String userSelfEvaluationDeptCode;

    @TableField("UserSelfEvaluationStatus")
    private Integer userSelfEvaluationStatus;

    @TableField("UserSelfEvaluationScore")
    private BigDecimal userSelfEvaluationScore;

    @TableField("UserSelfEvaluationFinishedTime")
    private Date userSelfEvaluationFinishedTime;

    @TableField("UserSelfEvaluationName")
    private String userSelfEvaluationName;

    @TableField("UserSelfEvaluationMonth")
    private Integer userSelfEvaluationMonth;

    @TableField("UserSelfEvaluationYear")
    private Integer userSelfEvaluationYear;

    @TableField("UserSelfEvaluationForHospLevel")
    private Long userSelfEvaluationForHospLevel;

    @TableField("UserSelfEvaluationIsGroup")
    private Boolean userSelfEvaluationIsGroup;

    @TableField("UserSelfEvaluationGroupDept")
    private String userSelfEvaluationGroupDept;

    @TableField("orgMasterId")
    private String orgMasterId;


}
