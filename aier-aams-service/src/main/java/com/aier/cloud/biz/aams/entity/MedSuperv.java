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
@TableName("MedSuperv")
@ApiModel(value = "MedSuperv对象", description = "")
public class MedSuperv implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "MedSupervId", type = IdType.AUTO)
    private Long medSupervId;

    @TableField("MedSupervName")
    private String medSupervName;

    @TableField("MedSupervType")
    private Integer medSupervType;

    @TableField("MedSupervOrgCode")
    private String medSupervOrgCode;

    @TableField("orgMasterId")
    private String orgMasterId;

    @TableField("MedSupervCreate_user")
    private String medsupervcreateUser;

    @TableField("MedSupervCreate_time")
    private Date medsupervcreateTime;

    @TableField("MedSupervUpdate_user")
    private String medsupervupdateUser;

    @TableField("MedSupervUpdate_time")
    private Date medsupervupdateTime;

    @TableField("MedSupervIsdlt")
    private Boolean medSupervIsdlt;

    @TableField("MedSupervFinishTime")
    private Date medSupervFinishTime;

    @TableField("MedSupervStatus")
    private Integer medSupervStatus;

    @TableField("MedSupervRectifyFinishTime")
    private Date medSupervRectifyFinishTime;

    @TableField("MedSupervRectifySubmit_user")
    private String medsupervrectifysubmitUser;

    @TableField("MedSupervScore")
    private BigDecimal medSupervScore;

    @TableField("MedSupervDeptCode")
    private String medSupervDeptCode;

    @TableField("MedSupervMonth")
    private Integer medSupervMonth;

    @TableField("MedSupervYear")
    private Integer medSupervYear;


}
