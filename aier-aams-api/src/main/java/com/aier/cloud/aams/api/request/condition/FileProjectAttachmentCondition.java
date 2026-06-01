package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class FileProjectAttachmentCondition extends PageCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long fileProjectAttachmentId;
    private Long fileProjectManageId;
    private Long attachmentMasterId;
    private String fileProjectAttachmentType;
    private Date fileprojectattachmentcreateTime;
    private Long fileProjectAttachmentUserId;
    private Integer fileProjectAttachmentIsdlt;
    private String fileProjectAttachmentUserName;
}
