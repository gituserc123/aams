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
@TableName("UserSelfRisk")
@ApiModel(value = "UserSelfRisk对象", description = "")
public class UserSelfRisk implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "UserSelfRiskId", type = IdType.AUTO)
    private Long userSelfRiskId;

    @TableField("UserSelfRiskLevel1")
    private String userSelfRiskLevel1;

    @TableField("UserSelfRiskLevel2")
    private String userSelfRiskLevel2;

    @TableField("UserSelfRiskLevel3")
    private String userSelfRiskLevel3;

    @TableField("UserSelfRiskDetail")
    private String userSelfRiskDetail;

    @TableField("UserSelfRiskScore")
    private Integer userSelfRiskScore;

    @TableField("UserSelfRiskDeductCriterion")
    private String userSelfRiskDeductCriterion;

    @TableField("UserSelfRiskDeptCode")
    private String userSelfRiskDeptCode;

    @TableField("UserSelfRiskCreate_time")
    private Date userselfriskcreateTime;

    @TableField("UserSelfRiskCreate_user")
    private String userselfriskcreateUser;

    @TableField("UserSelfRiskUpdate_time")
    private Date userselfriskupdateTime;

    @TableField("UserSelfRiskUpdate_user")
    private String userselfriskupdateUser;

    @TableField("UserSelfRiskIsdlt")
    private Boolean userSelfRiskIsdlt;

    @TableField("UserSelfRiskCode")
    private String userSelfRiskCode;

    @TableField("UserSelfRiskForHospLevel")
    private Long userSelfRiskForHospLevel;


}
