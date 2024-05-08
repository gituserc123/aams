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
@TableName("MedSupervDetail")
@ApiModel(value = "MedSupervDetail对象", description = "")
public class MedSupervDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "MedSupervDetailId", type = IdType.AUTO)
    private Long medSupervDetailId;

    @TableField("MedSupervId")
    private Long medSupervId;

    @TableField("UserSelfRiskId")
    private Long userSelfRiskId;

    @TableField("MedSupervDetailScore")
    private BigDecimal medSupervDetailScore;

    @TableField("MedSupervDetailDeductReason")
    private String medSupervDetailDeductReason;

    @TableField("MedSupervDetailRectifyUpdate_time")
    private Date medsupervdetailrectifyupdateTime;

    @TableField("MedSupervDetailRectifyUpdate_user")
    private String medsupervdetailrectifyupdateUser;

    @TableField("MedSupervDetailRectifyMeasures")
    private String medSupervDetailRectifyMeasures;

    @TableField("MedSupervDetailUpdate_time")
    private Date medsupervdetailupdateTime;

    @TableField("MedSupervDetailUpdate_user")
    private String medsupervdetailupdateUser;

    @TableField("MedSupervDetailCreate_time")
    private Date medsupervdetailcreateTime;

    @TableField("MedSupervDetailCreate_user")
    private String medsupervdetailcreateUser;

    @TableField("MedSupervDetailStatus")
    private Integer medSupervDetailStatus;

    @TableField("MedSupervDetailCustomHbText")
    private String medSupervDetailCustomHbText;

    @TableField("MedSupervDetailIsGroupHb")
    private Boolean medSupervDetailIsGroupHb;


}
