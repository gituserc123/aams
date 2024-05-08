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
@TableName("RiskFindTemplate")
@ApiModel(value = "RiskFindTemplate对象", description = "")
public class RiskFindTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "RiskFindTemplateId", type = IdType.AUTO)
    private Long riskFindTemplateId;

    @TableField("RiskFindTemplateDetail")
    private String riskFindTemplateDetail;

    @TableField("RiskId")
    private Long riskId;

    @TableField("RiskFindTemplateIsdlt")
    private Boolean riskFindTemplateIsdlt;

    @TableField("RiskFindTemplateCreate_user")
    private String riskfindtemplatecreateUser;

    @TableField("RiskFindTemplateCreate_time")
    private Date riskfindtemplatecreateTime;

    @TableField("RiskFindTemplateUpdate_User")
    private String riskfindtemplateupdateUser;

    @TableField("RiskFindTemplateUpdate_time")
    private Date riskfindtemplateupdateTime;


}
