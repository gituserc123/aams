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
@TableName("RiskCollectionLabel")
@ApiModel(value = "RiskCollectionLabel对象", description = "")
public class RiskCollectionLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "RiskCollectionLabelId", type = IdType.AUTO)
    private Long riskCollectionLabelId;

    @TableField("RiskCollectionLabelType")
    private String riskCollectionLabelType;

    @TableField("RiskCollectionLabelCode")
    private Integer riskCollectionLabelCode;

    @TableField("RiskCollectionLabelName")
    private String riskCollectionLabelName;

    @TableField("RiskCollectionLabelIsDlt")
    private Boolean riskCollectionLabelIsDlt;

    @TableField("RiskCollectionLabelUpdate_time")
    private Date riskcollectionlabelupdateTime;

    @TableField("RiskCollectionLabelCreate_User")
    private String riskcollectionlabelcreateUser;

    @TableField("RiskCollectionLabelCreate_time")
    private Date riskcollectionlabelcreateTime;

    @TableField("RiskCollectionLabelDesc")
    private String riskCollectionLabelDesc;


}
