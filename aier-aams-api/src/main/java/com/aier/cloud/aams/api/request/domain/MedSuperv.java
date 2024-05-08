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
public class MedSuperv extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private Long medSupervId;

    private String medSupervName;

    private Integer medSupervType;

    private String medSupervOrgCode;

    private String orgMasterId;

    private String medsupervcreateUser;

    private Date medsupervcreateTime;

    private String medsupervupdateUser;

    private Date medsupervupdateTime;

    private Boolean medSupervIsdlt;

    private Date medSupervFinishTime;

    private Integer medSupervStatus;

    private Date medSupervRectifyFinishTime;

    private String medsupervrectifysubmitUser;

    private BigDecimal medSupervScore;

    private String medSupervDeptCode;

    private Integer medSupervMonth;

    private Integer medSupervYear;


}
