package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

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
public class SecFunctionalityCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long secFunctionalityId;

    private String secFunctionalityKey;

    private String secFunctionalityDescription;

    private Integer secFunctionalityType;

    private Long secParentFunctionalityId;

    private Boolean secFunctionalityActive;


}
