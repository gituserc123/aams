package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
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
public class MedSupervDetail extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private Long medSupervDetailId;

    private Long medSupervId;

    private Long userSelfRiskId;

    private BigDecimal medSupervDetailScore;

    private String medSupervDetailDeductReason;

    private Date medsupervdetailrectifyupdateTime;

    private String medsupervdetailrectifyupdateUser;

    private String medSupervDetailRectifyMeasures;

    private Date medsupervdetailupdateTime;

    private String medsupervdetailupdateUser;

    private Date medsupervdetailcreateTime;

    private String medsupervdetailcreateUser;

    private Integer medSupervDetailStatus;

    private String medSupervDetailCustomHbText;

    private Boolean medSupervDetailIsGroupHb;


}
