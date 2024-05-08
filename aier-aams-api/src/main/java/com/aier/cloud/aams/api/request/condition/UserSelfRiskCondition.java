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
public class UserSelfRiskCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long userSelfRiskId;

    private String userSelfRiskLevel1;

    private String userSelfRiskLevel2;

    private String userSelfRiskLevel3;

    private String userSelfRiskDetail;

    private Integer userSelfRiskScore;

    private String userSelfRiskDeductCriterion;

    private String userSelfRiskDeptCode;

    private Date userselfriskcreateTime;

    private String userselfriskcreateUser;

    private Date userselfriskupdateTime;

    private String userselfriskupdateUser;

    private Boolean userSelfRiskIsdlt;

    private String userSelfRiskCode;

    private Long userSelfRiskForHospLevel;


}
