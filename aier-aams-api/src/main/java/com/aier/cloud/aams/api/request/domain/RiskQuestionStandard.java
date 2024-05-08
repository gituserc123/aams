package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
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
public class RiskQuestionStandard extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private Long riskQuestionStandardId;

    // 风险点Id
    private Long riskId;

    // 码值
    private Integer riskQuestionStandardCode;

    // 问题描述
    private String riskQuestionStandardDesc;

    // 冗余值字段
    private String riskQuestionStandardValue;

    // 备注
    private String riskQuestionStandardRemark;

    // 删除标识
    private Boolean riskQuestionStandardIsDlt;

    private String riskquestionstandardcreateUser;

    private Date riskquestionstandardcreateTime;

    private String riskquestionstandardupdateUser;

    private Date riskquestionstandardupdateTime;


}
