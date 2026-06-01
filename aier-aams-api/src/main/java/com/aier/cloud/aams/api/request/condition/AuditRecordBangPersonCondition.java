package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class AuditRecordBangPersonCondition extends PageCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long auditRecordBangPersonId;
    private Long auditRecordId;
    private Date auditrecordbangpersoncreateTime;
    private String auditrecordbangpersoncreateUser;
    private Integer auditrecordbangpersonisSent;
    private String auditRecordBangPersonOrgMasterId;
    private String auditRecordBangPersonUserMainCode;
    private String auditRecordBangPersonUserCode;
    private String auditRecordBangPersonUserName;
    private Long auditRecordBangPersonUserId;
    private String auditRecordBangPersonRecordType;
}
