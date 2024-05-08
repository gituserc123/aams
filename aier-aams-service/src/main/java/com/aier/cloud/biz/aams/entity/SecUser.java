package com.aier.cloud.biz.aams.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("SecUser")
@ApiModel(value = "SecUser对象", description = "")
public class SecUser implements Serializable {

    @TableId(value = "SecUserId", type = IdType.AUTO)
    private Long secUserId;

    @TableField("SecUserName")
    private String secUserName;

    @TableField("SecUserPassword")
    private String secUserPassword;

    @TableField("SecUserCode")
    private String secUserCode;

    @TableField("SecUserCreate_time")
    private Date secusercreateTime;

    @TableField("SecUserUpdate_time")
    private Date secuserupdateTime;

    @TableField("SecUserIsdlt")
    private Boolean secUserIsdlt;

    @TableField("SecUserMainCode")
    private String secUserMainCode;

    @TableField("SecUserPosCode")
    private String secUserPosCode;

    @TableField("SecUserLeaderPosCode")
    private String secUserLeaderPosCode;

    @TableField("SecUserPosName")
    private String secUserPosName;

    @TableField("deptMasterCode")
    private String deptMasterCode;

    @TableField("SecUserGender")
    private String secUserGender;

    @TableField("SecUserEmail")
    private String secUserEmail;

    @TableField("SecUserPhoneNumber")
    private String secUserPhoneNumber;

    @TableField("SecUserLoginCount")
    private Integer secUserLoginCount;

    @TableField("SecUserLastLoginTime")
    private Date secUserLastLoginTime;

    @TableField("SecUserLoginIp")
    private String secUserLoginIp;
}