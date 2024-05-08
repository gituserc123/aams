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
@TableName("RemindTemplate")
@ApiModel(value = "RemindTemplate对象", description = "")
public class RemindTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "RemindTemplateId", type = IdType.AUTO)
    private Long remindTemplateId;

    @TableField("RemindTemplateTheme")
    private String remindTemplateTheme;

    @TableField("RemindTemplateContent")
    private String remindTemplateContent;

    @TableField("RemindTemplateIsdlt")
    private Boolean remindTemplateIsdlt;

    @TableField("RemindTemplateCreate_time")
    private Date remindtemplatecreateTime;

    @TableField("RemindTemplateCreate_user")
    private String remindtemplatecreateUser;

    @TableField("RemindTemplateUpdate_time")
    private Date remindtemplateupdateTime;

    @TableField("RemindTemplateUpdate_user")
    private String remindtemplateupdateUser;


}
