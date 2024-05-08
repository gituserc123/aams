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
public class HospitalInfoIssueCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long hospitalInfoId;

    private String hospitalInfoIssueId;

    private String hospitalInfoIssueType;

    private String hospitalInfoIssueDetail;

    private Boolean hospitalInfoIssueIsdlt;

    private Date hospitalinfoissueupdateTime;

    private String hospitalinfoissueupdateUser;


}
