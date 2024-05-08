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
@TableName("HospitalInfoPerson")
@ApiModel(value = "HospitalInfoPerson对象", description = "")
public class HospitalInfoPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "HospitalInfoId", type = IdType.AUTO)
    private Long hospitalInfoId;

    @TableId(value = "HospitalInfoPersonId", type = IdType.AUTO)
    private String hospitalInfoPersonId;

    @TableField("HospitalInfoPersonType")
    private String hospitalInfoPersonType;

    @TableField("HospitalInfoPersonOnJobNum")
    private Integer hospitalInfoPersonOnJobNum;

    @TableField("HospitalInfoPersonLeaveJobNum")
    private Integer hospitalInfoPersonLeaveJobNum;

    @TableField("HospitalInfoPersonUpdate_time")
    private Date hospitalinfopersonupdateTime;

    @TableField("HospitalInfoPersonUpdate_user")
    private String hospitalinfopersonupdateUser;


}
