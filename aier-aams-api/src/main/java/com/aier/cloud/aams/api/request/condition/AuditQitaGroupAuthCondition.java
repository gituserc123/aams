package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class AuditQitaGroupAuthCondition extends PageCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long auditQitaGroupAuthId;
    private Long secUserId;
    private String auditQitaGroupAuthBusiCode;
    private String auditqitagroupauthcreateUser;
    private Date auditqitagroupauthcreateTime;
    private Integer auditQitaGroupAuthIsDlt;
    private String auditqitagroupauthupdateUser;
    private Date auditqitagroupauthupdateTime;
    private String auditQitaGroupAuthOrgMasterId;
}
