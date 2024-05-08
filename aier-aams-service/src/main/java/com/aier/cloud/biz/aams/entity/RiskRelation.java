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
@TableName("RiskRelation")
@ApiModel(value = "RiskRelation对象", description = "")
public class RiskRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "RiskRelationPreRiskId", type = IdType.AUTO)
    private Long riskRelationPreRiskId;

    @TableId(value = "RiskRelationRiskId", type = IdType.AUTO)
    private Long riskRelationRiskId;

    @TableField("RiskRelationCreate_time")
    private Date riskrelationcreateTime;


}
