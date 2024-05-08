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
@TableName("ReportAuthorized")
@ApiModel(value = "ReportAuthorized对象", description = "")
public class ReportAuthorized implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ReportAuthorizedId", type = IdType.AUTO)
    private Long reportAuthorizedId;

    @TableField("AuditRecordId")
    private Long auditRecordId;

    @TableField("SecUserId")
    private Long secUserId;

    @TableField("ReportAuthorizedCreate_time")
    private Date reportauthorizedcreateTime;

    @TableField("ReportAuthorizedCreate_user")
    private String reportauthorizedcreateUser;

    @TableField("ReportAuthorizedIsdlt")
    private Boolean reportAuthorizedIsdlt;

    @TableField("ReportAuthorizedUpdate_time")
    private Date reportauthorizedupdateTime;

    @TableField("ReportAuthorizedUpdate_user")
    private String reportauthorizedupdateUser;


}
