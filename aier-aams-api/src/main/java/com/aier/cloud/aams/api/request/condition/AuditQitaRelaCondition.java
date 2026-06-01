package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class AuditQitaRelaCondition extends PageCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long auditQitaRelaId;
    private Long auditRecordId;
    private String auditQitaRelaCode;
    private String auditQitaRelaName;
    private Integer auditQitaRelaType;
}
