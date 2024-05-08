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
@TableName("HospitalInfoIssue")
@ApiModel(value = "HospitalInfoIssue对象", description = "")
public class HospitalInfoIssue implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "HospitalInfoId", type = IdType.AUTO)
    private Long hospitalInfoId;

    @TableId(value = "HospitalInfoIssueId", type = IdType.AUTO)
    private String hospitalInfoIssueId;

    @TableField("HospitalInfoIssueType")
    private String hospitalInfoIssueType;

    @TableField("HospitalInfoIssueDetail")
    private String hospitalInfoIssueDetail;

    @TableField("HospitalInfoIssueIsdlt")
    private Boolean hospitalInfoIssueIsdlt;

    @TableField("HospitalInfoIssueUpdate_time")
    private Date hospitalinfoissueupdateTime;

    @TableField("HospitalInfoIssueUpdate_user")
    private String hospitalinfoissueupdateUser;


}
