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
@TableName("FxglGroupAuth")
@ApiModel(value = "FxglGroupAuth对象", description = "")
public class FxglGroupAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "FxglGroupAuthId", type = IdType.AUTO)
    private Long fxglGroupAuthId;

    @TableField("SecUserId")
    private Long secUserId;

    @TableField("FxglGroupAuthBusiCode")
    private String fxglGroupAuthBusiCode;

    @TableField("FxglGroupAuthCreate_user")
    private String fxglgroupauthcreateUser;

    @TableField("FxglGroupAuthCreate_time")
    private Date fxglgroupauthcreateTime;

    @TableField("FxglGroupAuthIsDlt")
    private Boolean fxglGroupAuthIsDlt;

    @TableField("FxglGroupAuthUpdate_user")
    private String fxglgroupauthupdateUser;

    @TableField("FxglGroupAuthUpdate_time")
    private Date fxglgroupauthupdateTime;

    @TableField("FxglGroupAuthOrgMasterId")
    private String fxglGroupAuthOrgMasterId;


}
