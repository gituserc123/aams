package com.aier.cloud.biz.aams.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:57
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("AttachmentMaster")
@ApiModel(value = "AttachmentMaster对象", description = "")
public class AttachmentMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AttachmentMasterId", type = IdType.AUTO)
    private Long attachmentMasterId;

    @TableField("AttachmentMasterName")
    private String attachmentMasterName;

    @TableField("AttachmentMasterType")
    private String attachmentMasterType;

    @TableField("AttachmentMasterLength")
    private Long attachmentMasterLength;

    @TableField("AttachmentMasterPath")
    private String attachmentMasterPath;

    @TableField("AttachmentMasterCreate_time")
    private Date attachmentmastercreateTime;

    @TableField("AttachmentMasterCreate_user")
    private String attachmentmastercreateUser;

    @TableField("AttachmentMasterStatus")
    private String attachmentMasterStatus;

    @TableField("AttachmentMasterRealName")
    private String attachmentMasterRealName;

    @TableField("AttachmentMasterIsdlt")
    private Boolean attachmentMasterIsdlt;


}
