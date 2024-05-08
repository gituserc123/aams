package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
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
public class WeChatRemindCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long weChatRemindId;

    private Long remindMsgId;

    private String weChatRemindRecipient;

    private Date weChatRemindTime;

    private Integer weChatRemindStatus;


}
