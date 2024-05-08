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
public class RiskCollectionLabelCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long riskCollectionLabelId;

    private String riskCollectionLabelType;

    private Integer riskCollectionLabelCode;

    private String riskCollectionLabelName;

    private Boolean riskCollectionLabelIsDlt;

    private Date riskcollectionlabelupdateTime;

    private String riskcollectionlabelcreateUser;

    private Date riskcollectionlabelcreateTime;

    private String riskCollectionLabelDesc;


}
