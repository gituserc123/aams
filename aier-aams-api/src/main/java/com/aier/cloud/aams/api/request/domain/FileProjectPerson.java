package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class FileProjectPerson extends BaseEntity implements Serializable {

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
