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
public class PreAuditItemCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long preAuditItemId;

    private String preAuditItemActionType;

    private String preAuditItemType;

    private String preAuditItemName;

    private String preAuditItemMethod;

    private String preAuditItemList;

    private String preAuditItemSource;

    private Boolean preAuditItemIsHasRiskId;

    private String preaudititemupdateUser;

    private Date preaudititemupdateTime;

    private Boolean preAuditItemIsdlt;

    private String preAuditItemCode;


}
