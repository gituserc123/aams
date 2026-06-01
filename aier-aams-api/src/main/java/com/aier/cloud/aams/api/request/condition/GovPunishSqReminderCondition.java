package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class GovPunishSqReminderCondition extends PageCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long govPunishSqReminderId;
    private String govPunishSqReminderOrgMasterId;
    private String govPunishSqReminderSqName;
    private String govPunishSqReminderName;
    private String govPunishSqReminderMainCode;
}
