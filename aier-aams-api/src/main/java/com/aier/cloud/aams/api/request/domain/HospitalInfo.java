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
public class HospitalInfo extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private Long hospitalInfoId;

    private String orgMasterId;

    private String hospitalInfoDesc;

    private String hospitalInfoQualification;

    private String hospitalInfoTarget;

    private String hospitalInfoAccouts;

    private String hospitalInfoThirdparty;

    private String hospitalInfoMyopiaprevention;

    private String hospitalinfoupdateUser;

    private Date hospitalinfoupdateTime;

    private Date hospitalInfoOpenDate;

    private String hospitalInfoCorporate;

    private BigDecimal hospitalInfoRegCapital;

    private BigDecimal hospitalInfoActualCapital;

    private BigDecimal hospitalInfoOperateArea;

    private BigDecimal hospitalInfoRentWithTax;

    private String hospitalInfoBedNum;

    private String hospitalInfoOperationRoom;

    private String hospitalInfoCommitteeMembers;

    private String hospitalInfoEquityStructure;

    private String hospitalInfoIsSelfBuild;

    private BigDecimal hospitalInfoResidentPopulation;

    private String hospitalInfoLongitude;

    private String hospitalInfoLatitude;

    private String hospitalInfoLevel;

    private Boolean hospitalInfoSpecIsMove;

    private Date hospitalInfoSpecMoveDate;

    private Boolean hospitalInfoSpecIsExtend;

    private Date hospitalInfoSpecExtendDate;

    private String hospitalInfoAuditRecordName;

    private String hospitalInfoRectificationResponse;

    private String hospitalInfoBusinessInformation;

    private String hospitalInfoPersonSituation;


}
