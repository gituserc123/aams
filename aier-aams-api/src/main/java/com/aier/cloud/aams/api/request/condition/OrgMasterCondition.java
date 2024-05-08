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
public class OrgMasterCondition extends PageCondition { 

    private static final long serialVersionUID = 1L;

    private String orgMasterId;

    private String orgMasterCode;

    private String orgMasterName;

    private String orgMasterShort;

    private String orgMasterRegion;

    private String orgMasterStatus;

    private String orgMasterModaility;

    private String orgMasterType;

    private String orgMasterParentCode;

    private Boolean orgMasterIsdlt;

    private Date orgmastercreateTime;

    private Date orgmasterupdateTime;


}
