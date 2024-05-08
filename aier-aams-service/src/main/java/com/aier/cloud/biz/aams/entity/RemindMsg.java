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
@TableName("RemindMsg")
@ApiModel(value = "RemindMsg对象", description = "")
public class RemindMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "RemindMsgId", type = IdType.AUTO)
    private Long remindMsgId;

    @TableField("RemindMsgTheme")
    private String remindMsgTheme;

    @TableField("RemindMsgContent")
    private String remindMsgContent;

    @TableField("RemindMsgCreate_time")
    private Date remindmsgcreateTime;

    @TableField("RemindMsgCreate_user")
    private String remindmsgcreateUser;


}
