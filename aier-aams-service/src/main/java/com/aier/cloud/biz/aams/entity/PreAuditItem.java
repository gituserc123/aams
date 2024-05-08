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
@TableName("PreAuditItem")
@ApiModel(value = "PreAuditItem对象", description = "")
public class PreAuditItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "PreAuditItemId", type = IdType.AUTO)
    private Long preAuditItemId;

    @TableField("PreAuditItemActionType")
    private String preAuditItemActionType;

    @TableField("PreAuditItemType")
    private String preAuditItemType;

    @TableField("PreAuditItemName")
    private String preAuditItemName;

    @TableField("PreAuditItemMethod")
    private String preAuditItemMethod;

    @TableField("PreAuditItemList")
    private String preAuditItemList;

    @TableField("PreAuditItemSource")
    private String preAuditItemSource;

    @TableField("PreAuditItemIsHasRiskId")
    private Boolean preAuditItemIsHasRiskId;

    @TableField("PreAuditItemUpdate_user")
    private String preaudititemupdateUser;

    @TableField("PreAuditItemUpdate_time")
    private Date preaudititemupdateTime;

    @TableField("PreAuditItemIsdlt")
    private Boolean preAuditItemIsdlt;

    @TableField("PreAuditItemCode")
    private String preAuditItemCode;


}
