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
@TableName("Feedbacks")
@ApiModel(value = "Feedbacks对象", description = "")
public class Feedbacks implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "FeedbacksId", type = IdType.AUTO)
    private Long feedbacksId;

    @TableField("FeedbacksDesc")
    private String feedbacksDesc;

    @TableField("FeedbacksStatus")
    private String feedbacksStatus;

    @TableField("FeedbacksCreate_user")
    private String feedbackscreateUser;

    @TableField("FeedbacksCreate_time")
    private Date feedbackscreateTime;

    @TableField("FeedbacksReply")
    private String feedbacksReply;

    @TableField("FeedbacksNeedDay")
    private Integer feedbacksNeedDay;


}
