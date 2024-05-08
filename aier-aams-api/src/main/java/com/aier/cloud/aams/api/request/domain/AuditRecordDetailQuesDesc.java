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
public class AuditRecordDetailQuesDesc extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private Long auditRecordDetailQuesDescId;

    // 审计明细ID
    private Long auditRecordDetailId;

    // 问题前缀编码值
    private String auditRecordDetailQuesDescPreSuffix;

    // 问题后缀编码值
    private String auditRecordDetailQuesDescLastSuffix;

    // 组合后文本
    private String auditRecordDetailQuesDescTxt;

    private Date auditRecordDetailQuesDescCreateTime;


}
