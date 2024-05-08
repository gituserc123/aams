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
@TableName("SelfUserBussinessType")
@ApiModel(value = "SelfUserBussinessType对象", description = "")
public class SelfUserBussinessType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "SelfUserBussinessTypeId", type = IdType.AUTO)
    private Long selfUserBussinessTypeId;

    @TableField("SecUserId")
    private Long secUserId;

    @TableField("SelfUserBussinessTypeBussinessType")
    private String selfUserBussinessTypeBussinessType;


}
