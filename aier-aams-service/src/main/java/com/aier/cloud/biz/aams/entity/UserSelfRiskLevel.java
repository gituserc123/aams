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
@TableName("UserSelfRiskLevel")
@ApiModel(value = "UserSelfRiskLevel对象", description = "")
public class UserSelfRiskLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "UserSelfRiskLevelId", type = IdType.AUTO)
    private Long userSelfRiskLevelId;

    @TableField("UserSelfRiskLevelName")
    private String userSelfRiskLevelName;

    @TableField("UserSelfRiskLevelDeptCode")
    private String userSelfRiskLevelDeptCode;

    @TableField("UserSelfRiskLevelCreate_time")
    private Date userselfrisklevelcreateTime;

    @TableField("UserSelfRiskLevelCreate_user")
    private String userselfrisklevelcreateUser;

    @TableField("UserSelfRiskLevelUpdate_time")
    private Date userselfrisklevelupdateTime;

    @TableField("UserSelfRiskLevelUpdate_user")
    private String userselfrisklevelupdateUser;

    @TableField("UserSelfRiskLevelIsdlt")
    private Boolean userSelfRiskLevelIsdlt;

    @TableField("UserSelfRiskLevelCode")
    private String userSelfRiskLevelCode;


}
