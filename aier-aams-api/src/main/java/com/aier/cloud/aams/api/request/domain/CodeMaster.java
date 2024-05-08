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
public class CodeMaster extends BaseEntity implements Serializable { 

    private static final long serialVersionUID = 1L;

    private Long codeMasterId;

    private String codeMasterCode;

    private String codeMasterName;

    private String codeMasterType;

    private Boolean codeMasterIsdlt;

    private Date codemastercreateTime;

    private Date codemasterupdateTime;

    private String codeMasterTypeCode;

    private String codeMasterNameDesc;


}
