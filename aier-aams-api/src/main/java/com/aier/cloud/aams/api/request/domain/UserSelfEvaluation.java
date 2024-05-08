package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
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
public class UserSelfEvaluation extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private Long userSelfEvaluationId;

    private Date userselfevaluationcreateTime;

    private String userselfevaluationcreateUser;

    private Date userselfevaluationupdateTime;

    private String userselfevaluationupdateUser;

    private Boolean userSelfEvaluationIsdlt;

    private String userSelfEvaluationDeptCode;

    private Integer userSelfEvaluationStatus;

    private BigDecimal userSelfEvaluationScore;

    private Date userSelfEvaluationFinishedTime;

    private String userSelfEvaluationName;

    private Integer userSelfEvaluationMonth;

    private Integer userSelfEvaluationYear;

    private Long userSelfEvaluationForHospLevel;

    private Boolean userSelfEvaluationIsGroup;

    private String userSelfEvaluationGroupDept;

    private String orgMasterId;


}
