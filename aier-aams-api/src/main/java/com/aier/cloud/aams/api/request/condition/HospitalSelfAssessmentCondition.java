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
public class HospitalSelfAssessmentCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long hospitalSelfAssessmentId;

    private String hospitalSelfAssessmentName;

    private Integer hospitalSelfAssessmentType;

    private Date hospitalselfassessmentcreateTime;

    private String hospitalselfassessmentcreateUser;

    private Integer hospitalSelfAssessmentStatus;

    private String hospitalSelfAssessmentOrgCode;

    private Boolean hospitalSelfAssessmentIsDlt;

    private Date hospitalselfassessmentupdateTime;

    private String hospitalselfassessmentupdateUser;


}
