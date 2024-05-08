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
@TableName("UserSelfDept")
@ApiModel(value = "UserSelfDept对象", description = "")
public class UserSelfDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "UserSelfDeptId", type = IdType.AUTO)
    private Long userSelfDeptId;

    @TableField("UserSelfDeptCode")
    private String userSelfDeptCode;

    @TableField("SecUserId")
    private Long secUserId;


}
