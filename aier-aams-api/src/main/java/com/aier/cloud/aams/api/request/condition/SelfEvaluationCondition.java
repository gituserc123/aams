package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
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
public class SelfEvaluationCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long selfEvaluationId;

    private Date selfevaluationcreateTime;

    private String selfevaluationcreateUser;

    private Integer selfEvaluationScore;

    private String selfEvaluationResultDesc;

    private String selfevaluationupdateUser;

    private Date selfevaluationupdateTime;

    private Date selfEvaluationFinishedTime;

    private Integer selfEvaluationStatus;

    private String orgMasterId;

    private Boolean selfEvaluationIsdlt;

    private Integer selfEvaluationType;

    private Long selfEvaluationForHospType;

    private String selfEvaluationName;

    private Integer selfEvaluationMonth;

    private Integer selfEvaluationYear;

    private Date selfEvaluationSubmitDate;

    private Boolean selfEvaluationIsClosed;


}
