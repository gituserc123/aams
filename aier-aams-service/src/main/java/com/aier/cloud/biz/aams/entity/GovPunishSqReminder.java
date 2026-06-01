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

@Getter
@Setter
@Accessors(chain = true)
@TableName("GovPunishSqReminder")
@ApiModel(value = "GovPunishSqReminder对象", description = "")
public class GovPunishSqReminder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "GovPunishSqReminderId", type = IdType.AUTO)
    private Long govPunishSqReminderId;

    @TableField("GovPunishSqReminderOrgMasterId")
    private String govPunishSqReminderOrgMasterId;

    @TableField("GovPunishSqReminderSqName")
    private String govPunishSqReminderSqName;

    @TableField("GovPunishSqReminderName")
    private String govPunishSqReminderName;

    @TableField("GovPunishSqReminderMainCode")
    private String govPunishSqReminderMainCode;
}
