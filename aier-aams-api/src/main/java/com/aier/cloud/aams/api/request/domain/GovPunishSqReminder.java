package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class GovPunishSqReminder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long govPunishSqReminderId;
    private String govPunishSqReminderOrgMasterId;
    private String govPunishSqReminderSqName;
    private String govPunishSqReminderName;
    private String govPunishSqReminderMainCode;
}
