package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
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
public class RiskFindTemplate extends BaseEntity implements Serializable { 

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
