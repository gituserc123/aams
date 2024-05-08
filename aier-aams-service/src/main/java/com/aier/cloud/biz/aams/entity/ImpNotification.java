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
@TableName("ImpNotification")
@ApiModel(value = "ImpNotification对象", description = "")
public class ImpNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ImpNotificationId", type = IdType.AUTO)
    private Long impNotificationId;

    @TableField("ImpNotificationUpdate_user")
    private String impnotificationupdateUser;

    @TableField("ImpNotificationUpdate_time")
    private Date impnotificationupdateTime;

    @TableField("ImpNotificationDetail")
    private String impNotificationDetail;


}
