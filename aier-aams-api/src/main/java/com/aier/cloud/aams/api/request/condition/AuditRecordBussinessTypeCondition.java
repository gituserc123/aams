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
public class AuditRecordBussinessTypeCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long auditRecordBussinessTypeId;

    private Long auditRecordId;

    private String auditRecordBussinessTypeSuggestion;

    private Boolean auditRecordBussinessTypeIsdlt;

    private String auditrecordbussinesstypecreateUser;

    private String auditrecordbussinesstypeupdateUser;

    private Date auditrecordbussinesstypecreateTime;

    private Date auditrecordbussinesstypeupdateTime;

    private String auditRecordBussinessTypeCode;

    private Long auditRecordBussinessTypeAttachmentId;

    private String auditRecordBussinessTypeRemarks;

    private Integer auditRecordBussinessTypeScore;

    private Date auditRecordBussinessTypeConfirmTime;

    private String auditRecordBussinessTypeConfirmUser;

    private Boolean auditRecordBussinessTypeOpenToHos;

    private Date auditRecordBussinessTypeOpenToHosDeadlin;

    private String auditrecordbussinesstyperemarksUser;

    private Date auditrecordbussinesstyperemarksTime;


}
