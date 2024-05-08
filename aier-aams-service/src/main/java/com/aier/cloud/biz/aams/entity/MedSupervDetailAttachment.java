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
@Accessors(chain = true)
@TableName("MedSupervDetailAttachment")
@ApiModel(value = "MedSupervDetailAttachment对象", description = "")
public class MedSupervDetailAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "MedSupervDetailId", type = IdType.AUTO)
    private Long medSupervDetailId;

    @TableId(value = "MedSupervDetailAttachmentId", type = IdType.AUTO)
    private String medSupervDetailAttachmentId;

    @TableField("AttachmentMasterId")
    private Long attachmentMasterId;

    @TableField("MedSupervDetailAttachmentType")
    private String medSupervDetailAttachmentType;


}
