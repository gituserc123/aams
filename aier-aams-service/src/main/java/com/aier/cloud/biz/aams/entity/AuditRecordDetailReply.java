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
@TableName("AuditRecordDetailReply")
@ApiModel(value = "AuditRecordDetailReply对象", description = "")
public class AuditRecordDetailReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "AuditRecordDetailReplyID", type = IdType.AUTO)
    private String auditRecordDetailReplyID;

    @TableField("AuditRecordDetailId")
    private Long auditRecordDetailId;

    @TableField("AuditRecordReplyID1")
    private String auditRecordReplyID1;

    @TableField("AuditRecordDetailReplyEvaluate_user")
    private String auditrecorddetailreplyevaluateUser;

    @TableField("AuditRecordDetailReplyEvaluate_time")
    private Date auditrecorddetailreplyevaluateTime;

    @TableField("AuditRecordDetailReplyEvaluateLx")
    private Integer auditRecordDetailReplyEvaluateLx;

    @TableField("AuditRecordDetailReplyEvaluatYxx")
    private Integer auditRecordDetailReplyEvaluatYxx;

    @TableField("AuditRecordDetailReplyEvaluatXx")
    private String auditRecordDetailReplyEvaluatXx;

    @TableField("AuditRecordDetailReplyBS")
    private Integer auditRecordDetailReplyBS;


}
