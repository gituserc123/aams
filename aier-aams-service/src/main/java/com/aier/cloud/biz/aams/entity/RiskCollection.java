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
@TableName("RiskCollection")
@ApiModel(value = "RiskCollection对象", description = "")
public class RiskCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "RiskCollectionId", type = IdType.AUTO)
    private Long riskCollectionId;

    @TableField("RiskId")
    private Long riskId;

    @TableField("RiskCollectionLabelId")
    private Long riskCollectionLabelId;

    @TableField("RiskCollectionOrder")
    private Integer riskCollectionOrder;

    @TableField("RiskCollectionIsDlt")
    private Boolean riskCollectionIsDlt;

    @TableField("RiskCollectionUpdate_time")
    private Date riskcollectionupdateTime;

    @TableField("RiskCollectionCreate_User")
    private String riskcollectioncreateUser;

    @TableField("RiskCollectionCreate_time")
    private Date riskcollectioncreateTime;


}
