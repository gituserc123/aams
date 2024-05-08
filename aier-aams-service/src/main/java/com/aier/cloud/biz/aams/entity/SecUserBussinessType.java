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
@TableName("SecUserBussinessType")
@ApiModel(value = "SecUserBussinessType对象", description = "")
public class SecUserBussinessType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "SecUserBussinessTypeId", type = IdType.AUTO)
    private Long secUserBussinessTypeId;

    @TableField("SecUserId")
    private Long secUserId;

    @TableField("SecUserBussinessTypeCode")
    private String secUserBussinessTypeCode;

    @TableField("SecUserBussinessTypeCreate_user")
    private String secuserbussinesstypecreateUser;

    @TableField("SecUserBussinessTypeCreate_time")
    private Date secuserbussinesstypecreateTime;

    @TableField("SecUserBussinessTypeIsDlt")
    private Boolean secUserBussinessTypeIsDlt;

    @TableField("SecUserBussinessTypeUpdate_time")
    private Date secuserbussinesstypeupdateTime;

    @TableField("SecUserBussinessTypeUpdate_user")
    private String secuserbussinesstypeupdateUser;

    @TableField("SecUserBussinessTypeOrgMasterId")
    private String secUserBussinessTypeOrgMasterId;


}
