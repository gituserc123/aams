package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
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
public class RiskScoreStandardCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long riskScoreStandardId;

    // 审计手册Id/自评手册Id
    private Long riskScoreStandardManualId;

    // 类型:1-审计手册/2-自评手册
    private String riskScoreStandardType;

    // 分值
    private Integer riskScoreStandardScore;

    // 扣分描述
    private String riskScoreStandardDesc;

    // 码值
    private Integer riskScoreStandardCode;

    // 上级码值
    private Integer riskScoreStandardParentCode;

    // 冗余值字段
    private String riskScoreStandardValue;

    // 删除标识
    private Boolean riskScoreStandardIsDlt;

    private String riskscorestandardcreateUser;

    private Date riskscorestandardcreateTime;

    private String riskscorestandardupdateUser;

    private Date riskscorestandardupdateTime;


}
