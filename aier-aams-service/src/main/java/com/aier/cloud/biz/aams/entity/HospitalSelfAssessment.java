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
@TableName("HospitalSelfAssessment")
@ApiModel(value = "HospitalSelfAssessment对象", description = "")
public class HospitalSelfAssessment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "HospitalSelfAssessmentId", type = IdType.AUTO)
    private Long hospitalSelfAssessmentId;

    @TableField("HospitalSelfAssessmentName")
    private String hospitalSelfAssessmentName;

    @TableField("HospitalSelfAssessmentType")
    private Integer hospitalSelfAssessmentType;

    @TableField("HospitalSelfAssessmentCreate_time")
    private Date hospitalselfassessmentcreateTime;

    @TableField("HospitalSelfAssessmentCreate_user")
    private String hospitalselfassessmentcreateUser;

    @TableField("HospitalSelfAssessmentStatus")
    private Integer hospitalSelfAssessmentStatus;

    @TableField("HospitalSelfAssessmentOrgCode")
    private String hospitalSelfAssessmentOrgCode;

    @TableField("HospitalSelfAssessmentIsDlt")
    private Boolean hospitalSelfAssessmentIsDlt;

    @TableField("HospitalSelfAssessmentUpdate_time")
    private Date hospitalselfassessmentupdateTime;

    @TableField("HospitalSelfAssessmentUpdate_user")
    private String hospitalselfassessmentupdateUser;


}
