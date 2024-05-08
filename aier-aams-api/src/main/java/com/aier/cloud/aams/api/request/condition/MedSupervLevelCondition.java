package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class MedSupervLevelCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long medSupervLevelId;

    private Long medSupervId;

    private String medSupervLevelCode;

    private BigDecimal medSupervLevelScore;

    private Date medsupervlevelrectifyupdateTime;

    private String medsupervlevelrectifyupdateUser;

    private Integer medSupervLevelRectifyStatus;

    private Date medsupervlevelupdateTime;

    private String medsupervlevelupdateUser;


}
