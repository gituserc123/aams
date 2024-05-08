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
@TableName("AuditRecordDetailReplyXxb")
@ApiModel(value = "AuditRecordDetailReplyXxb对象", description = "")
public class AuditRecordDetailReplyXxb implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditRecordDetailReplyXxbID", type = IdType.AUTO)
    private String auditRecordDetailReplyXxbID;

    @TableField("AuditRecordDetailReplyID")
    private String auditRecordDetailReplyID;

    @TableField("AuditRecordDetailReplyXxbHfxx")
    private String auditRecordDetailReplyXxbHfxx;

    @TableField("AuditRecordDetailReplyXxb_Hfuser")
    private String auditrecorddetailreplyxxbHfuser;

    @TableField("AuditRecordDetailReplyXxb_Hftime")
    private Date auditrecorddetailreplyxxbHftime;

    @TableField("AuditRecordDetailReplyXxbOverdueReason")
    private String auditRecordDetailReplyXxbOverdueReason;

    @TableField("AuditRecordDetailReplyXxbSJFinishTime")
    private Date auditRecordDetailReplyXxbSJFinishTime;

    @TableField("AuditRecordDetailReplyXxbStatus")
    private Integer auditRecordDetailReplyXxbStatus;

    @TableField("AuditRecordDetailReplyXxbBS")
    private Integer auditRecordDetailReplyXxbBS;

    @TableField("AuditRecordDetailReplyXxbSqpj_user")
    private String auditrecorddetailreplyxxbsqpjUser;

    @TableField("AuditRecordDetailReplyXxbSqpj_time")
    private Date auditrecorddetailreplyxxbsqpjTime;

    @TableField("AuditRecordDetailReplyXxbSqpjjg")
    private Integer auditRecordDetailReplyXxbSqpjjg;

    @TableField("AuditRecordDetailReplyXxbSqpjxx")
    private String auditRecordDetailReplyXxbSqpjxx;

    @TableField("AuditRecordDetailReplyXxbHFBS")
    private Integer auditRecordDetailReplyXxbHFBS;

    @TableField("AuditRecordDetailReplyXxbXGBS")
    private Integer auditRecordDetailReplyXxbXGBS;


}
