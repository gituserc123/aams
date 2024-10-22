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
public class SelfRisk extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private Long selfRiskId;

    private String selfRiskCode;

    private String selfRiskTitle;

    private String selfRiskDesc;

    private String selfRiskBussinessType;

    private String selfRiskFirstLevel;

    private String selfRiskSecondLevel;

    private String selfRiskEvalucationBasis;

    private String selfRiskMethod;

    private String selfRiskGuide;

    private String selfRiskType;

    private Boolean selfRiskIsdlt;

    private Date selfriskcreateTime;

    private String selfriskcreateUser;

    private Date selfriskupdateTime;

    private String selfriskupdateUser;

    private Long selfRiskForHospType;

    private String selfRiskDeductCriterion;

    private Integer selfRiskScore;

    private Long selfRiskIdShow;

    private String selfRiskBussinessTypeTxt;

    private Boolean hospital;

    private Boolean outPatient;

    private Boolean eyeOpticClinic;

    private Boolean spectacleStore;



}
