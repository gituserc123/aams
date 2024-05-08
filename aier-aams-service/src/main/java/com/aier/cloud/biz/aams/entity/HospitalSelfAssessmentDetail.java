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
@TableName("HospitalSelfAssessmentDetail")
@ApiModel(value = "HospitalSelfAssessmentDetail对象", description = "")
public class HospitalSelfAssessmentDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "HospitalSelfAssessmentDetailId", type = IdType.AUTO)
    private Long hospitalSelfAssessmentDetailId;

    @TableField("HospitalSelfAssessmentId")
    private Long hospitalSelfAssessmentId;

    @TableField("RiskId")
    private Long riskId;

    @TableField("HospitalSelfAssessmentDetailResult")
    private String hospitalSelfAssessmentDetailResult;

    @TableField("HospitalSelfAssessmentDetailResultDesc")
    private String hospitalSelfAssessmentDetailResultDesc;

    @TableField("HospitalSelfAssessmentDetailCreate_time")
    private Date hospitalselfassessmentdetailcreateTime;

    @TableField("HospitalSelfAssessmentDetailCreate_user")
    private String hospitalselfassessmentdetailcreateUser;

    @TableField("HospitalSelfAssessmentDetailUpdate_time")
    private Date hospitalselfassessmentdetailupdateTime;

    @TableField("HospitalSelfAssessmentDetailUpdate_user")
    private String hospitalselfassessmentdetailupdateUser;


}
