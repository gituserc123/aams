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
public class Risk extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private Long riskId;

    private String riskName;

    private String riskDesc;

    private String riskBussinessType;

    private String riskProject;

    private String riskLevel;

    private Boolean riskIsdlt;

    private Date riskcreateTime;

    private String riskcreateUser;

    private Date riskupdateTime;

    private String riskupdateUser;

    private Boolean riskIsPost;

    private String riskMethod;

    private String riskInstitution;

    private Boolean riskRemote;

    private String riskType;

    private String riskCode;

    private Long riskCapability;

    private Boolean riskSenstivity;

    private String riskRectifyType;

    private Boolean riskIsRandom;

    private Long selfRiskId;

    private String riskSenstivityDesc;

    private String riskEmergencyRef;

    private String riskRectifyAttribute;

    private String riskCategory;

    private Boolean riskIsQueDescStd;

    private String riskDigitalModel;


}
