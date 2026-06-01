package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class FileProjectManageCondition extends PageCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long fileProjectManageId;
    private String fileProjectManageName;
    private String fileProjectManageType;
    private Long fileprojectmanagecreateUserId;
    private Date fileprojectmanagecreateTime;
    private String fileprojectmanagecreateUserName;
    private Integer fileProjectManageIsdlt;
    private Date fileprojectmanageupdateTime;
    private String fileProjectManageOrgmasterId;
}
