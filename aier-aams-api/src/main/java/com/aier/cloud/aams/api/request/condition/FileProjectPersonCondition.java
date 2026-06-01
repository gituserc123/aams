package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class FileProjectPersonCondition extends PageCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long fileProjectPersonId;
    private Long fileProjectManageId;
    private Long fileProjectPersonUserId;
    private String fileProjectPersonUserName;
    private String fileProjectPersonUserCode;
    private String fileProjectPersonUserMainCode;
    private String fileProjectPersonOrgMasterId;
    private String fileProjectPersonUserType;
    private Long fileprojectpersoncreateUserId;
    private Date fileprojectpersoncreateTime;
}
