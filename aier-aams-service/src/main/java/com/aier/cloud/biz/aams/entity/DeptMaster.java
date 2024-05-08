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
@TableName("DeptMaster")
@ApiModel(value = "DeptMaster对象", description = "")
public class DeptMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "deptMasterCode", type = IdType.AUTO)
    private String deptMasterCode;

    @TableField("deptMasterName")
    private String deptMasterName;

    @TableField("OrgMasterId")
    private String orgMasterId;

    @TableField("deptMasterParentCode")
    private String deptMasterParentCode;

    @TableField("deptMasterIsdlt")
    private Boolean deptMasterIsdlt;

    @TableField("DeptMasterCreate_time")
    private Date deptmastercreateTime;

    @TableField("DeptMasterUpdate_time")
    private Date deptmasterupdateTime;


}
