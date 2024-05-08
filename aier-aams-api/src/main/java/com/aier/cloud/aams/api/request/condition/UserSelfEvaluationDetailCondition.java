package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
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
public class UserSelfEvaluationDetailCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long userSelfEvaluationDetailId;

    private Date userselfevaluationdetailcreateTime;

    private String userselfevaluationdetailcreateUser;

    private Date userselfevaluationdetailupdateTime;

    private String userselfevaluationdetailupdateUser;

    private BigDecimal userSelfEvaluationDetailScore;

    private String userSelfEvaluationDetailNonScoreDesc;

    private Long userSelfEvaluationId;

    private Long userSelfRiskId;

    private String userSelfEvaluationDetailMeasures;

    private String userSelfEvaluationDetailResult;


}
