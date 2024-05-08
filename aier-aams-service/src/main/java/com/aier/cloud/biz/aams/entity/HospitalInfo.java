package com.aier.cloud.biz.aams.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@TableName("HospitalInfo")
@ApiModel(value = "HospitalInfo对象", description = "")
public class HospitalInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "HospitalInfoId", type = IdType.AUTO)
    private Long hospitalInfoId;

    @TableField("orgMasterId")
    private String orgMasterId;

    @TableField("HospitalInfoDesc")
    private String hospitalInfoDesc;

    @TableField("HospitalInfoQualification")
    private String hospitalInfoQualification;

    @TableField("HospitalInfoTarget")
    private String hospitalInfoTarget;

    @TableField("HospitalInfoAccouts")
    private String hospitalInfoAccouts;

    @TableField("HospitalInfoThirdparty")
    private String hospitalInfoThirdparty;

    @TableField("HospitalInfoMyopiaprevention")
    private String hospitalInfoMyopiaprevention;

    @TableField("HospitalInfoUpdate_user")
    private String hospitalinfoupdateUser;

    @TableField("HospitalInfoUpdate_time")
    private Date hospitalinfoupdateTime;

    @TableField("HospitalInfoOpenDate")
    private Date hospitalInfoOpenDate;

    @TableField("HospitalInfoCorporate")
    private String hospitalInfoCorporate;

    @TableField("HospitalInfoRegCapital")
    private BigDecimal hospitalInfoRegCapital;

    @TableField("HospitalInfoActualCapital")
    private BigDecimal hospitalInfoActualCapital;

    @TableField("HospitalInfoOperateArea")
    private BigDecimal hospitalInfoOperateArea;

    @TableField("HospitalInfoRentWithTax")
    private BigDecimal hospitalInfoRentWithTax;

    @TableField("HospitalInfoBedNum")
    private String hospitalInfoBedNum;

    @TableField("HospitalInfoOperationRoom")
    private String hospitalInfoOperationRoom;

    @TableField("HospitalInfoCommitteeMembers")
    private String hospitalInfoCommitteeMembers;

    @TableField("HospitalInfoEquityStructure")
    private String hospitalInfoEquityStructure;

    @TableField("HospitalInfoIsSelfBuild")
    private String hospitalInfoIsSelfBuild;

    @TableField("HospitalInfoResidentPopulation")
    private BigDecimal hospitalInfoResidentPopulation;

    @TableField("HospitalInfoLongitude")
    private String hospitalInfoLongitude;

    @TableField("HospitalInfoLatitude")
    private String hospitalInfoLatitude;

    @TableField("HospitalInfoLevel")
    private String hospitalInfoLevel;

    @TableField("HospitalInfoSpecIsMove")
    private Boolean hospitalInfoSpecIsMove;

    @TableField("HospitalInfoSpecMoveDate")
    private Date hospitalInfoSpecMoveDate;

    @TableField("HospitalInfoSpecIsExtend")
    private Boolean hospitalInfoSpecIsExtend;

    @TableField("HospitalInfoSpecExtendDate")
    private Date hospitalInfoSpecExtendDate;

    @TableField("HospitalInfoAuditRecordName")
    private String hospitalInfoAuditRecordName;

    @TableField("HospitalInfoRectificationResponse")
    private String hospitalInfoRectificationResponse;

    @TableField("HospitalInfoBusinessInformation")
    private String hospitalInfoBusinessInformation;

    @TableField("HospitalInfoPersonSituation")
    private String hospitalInfoPersonSituation;


}
