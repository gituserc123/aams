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
 * @since 2024-04-20 11:47:37
 */
@Getter
@Setter
public class DeptMaster extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private String deptMasterCode;

    private String deptMasterName;

    private String orgMasterId;

    private String deptMasterParentCode;

    private Boolean deptMasterIsdlt;

    private Date deptmastercreateTime;

    private Date deptmasterupdateTime;


}
