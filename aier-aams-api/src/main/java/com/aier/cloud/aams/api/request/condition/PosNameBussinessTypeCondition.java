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
public class PosNameBussinessTypeCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private Long posNameBussinessTypeId;

    private String userPosName;

    private String bussinessTypeCode;

    private String bussinessTypeName;


}
