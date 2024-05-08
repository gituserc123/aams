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
 * @since 2024-04-20 11:47:37
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("CodeMaster")
@ApiModel(value = "CodeMaster对象", description = "")
public class CodeMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "CodeMasterId", type = IdType.AUTO)
    private Long codeMasterId;

    @TableField("CodeMasterCode")
    private String codeMasterCode;

    @TableField("CodeMasterName")
    private String codeMasterName;

    @TableField("CodeMasterType")
    private String codeMasterType;

    @TableField("CodeMasterIsdlt")
    private Boolean codeMasterIsdlt;

    @TableField("CodeMasterCreate_time")
    private Date codemastercreateTime;

    @TableField("CodeMasterUpdate_time")
    private Date codemasterupdateTime;

    @TableField("CodeMasterTypeCode")
    private String codeMasterTypeCode;

    @TableField("CodeMasterNameDesc")
    private String codeMasterNameDesc;


}
