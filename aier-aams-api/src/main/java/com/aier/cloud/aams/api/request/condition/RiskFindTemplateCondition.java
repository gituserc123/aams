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
public class RiskFindTemplateCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long riskFindTemplateId;

    private String riskFindTemplateDetail;

    private Long riskId;

    private Boolean riskFindTemplateIsdlt;

    private String riskfindtemplatecreateUser;

    private Date riskfindtemplatecreateTime;

    private String riskfindtemplateupdateUser;

    private Date riskfindtemplateupdateTime;


}