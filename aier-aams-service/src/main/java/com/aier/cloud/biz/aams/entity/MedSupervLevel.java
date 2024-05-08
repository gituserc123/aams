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
@TableName("MedSupervLevel")
@ApiModel(value = "MedSupervLevel对象", description = "")
public class MedSupervLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "MedSupervLevelId", type = IdType.AUTO)
    private Long medSupervLevelId;

    @TableField("MedSupervId")
    private Long medSupervId;

    @TableField("MedSupervLevelCode")
    private String medSupervLevelCode;

    @TableField("MedSupervLevelScore")
    private BigDecimal medSupervLevelScore;

    @TableField("MedSupervLevelRectifyUpdate_time")
    private Date medsupervlevelrectifyupdateTime;

    @TableField("MedSupervLevelRectifyUpdate_user")
    private String medsupervlevelrectifyupdateUser;

    @TableField("MedSupervLevelRectifyStatus")
    private Integer medSupervLevelRectifyStatus;

    @TableField("MedSupervLevelUpdate_time")
    private Date medsupervlevelupdateTime;

    @TableField("MedSupervLevelUpdate_user")
    private String medsupervlevelupdateUser;


}
