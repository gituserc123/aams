package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class UserSELevel extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private Long userSELevelId;

    private Long userSelfEvaluationId;

    private String userSELevelCode;

    private BigDecimal userSELevelScore;

    private Date userselevelcreateTime;

    private String userselevelcreateUser;


}
