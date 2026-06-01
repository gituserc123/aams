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

@Getter
@Setter
@Accessors(chain = true)
@TableName("FileProjectAttachment")
@ApiModel(value = "FileProjectAttachment对象", description = "")
public class FileProjectAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "FileProjectAttachmentId", type = IdType.AUTO)
    private Long fileProjectAttachmentId;

    @TableField("FileProjectManageId")
    private Long fileProjectManageId;

    @TableField("AttachmentMasterId")
    private Long attachmentMasterId;

    @TableField("FileProjectAttachmentType")
    private String fileProjectAttachmentType;

    @TableField("FileProjectAttachmentCreate_time")
    private Date fileprojectattachmentcreateTime;

    @TableField("FileProjectAttachmentUserId")
    private Long fileProjectAttachmentUserId;

    @TableField("FileProjectAttachmentIsdlt")
    private Integer fileProjectAttachmentIsdlt;

    @TableField("FileProjectAttachmentUserName")
    private String fileProjectAttachmentUserName;
}
