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
@TableName("PreAuditFocus")
@ApiModel(value = "PreAuditFocus对象", description = "")
public class PreAuditFocus implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "PreAuditFocusId", type = IdType.AUTO)
    private Long preAuditFocusId;

    @TableField("AuditRecordId")
    private Long auditRecordId;

    @TableField("PreAuditFocusContent")
    private String preAuditFocusContent;

    @TableField("PreAuditFocusResult")
    private String preAuditFocusResult;

    @TableField("PreAuditFocusiIsDlt")
    private Boolean preAuditFocusiIsDlt;

    @TableField("PreAuditFocusUpdate_time")
    private Date preauditfocusupdateTime;

    @TableField("PreAuditFocusUpdate_user")
    private String preauditfocusupdateUser;


}
