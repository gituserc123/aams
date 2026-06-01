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

@Getter
@Setter
@Accessors(chain = true)
@TableName("FileProjectManage")
@ApiModel(value = "FileProjectManage对象", description = "")
public class FileProjectManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "FileProjectManageId", type = IdType.AUTO)
    private Long fileProjectManageId;

    @TableField("FileProjectManageName")
    private String fileProjectManageName;

    @TableField("FileProjectManageType")
    private String fileProjectManageType;

    @TableField("FileProjectManageCreate_UserId")
    private Long fileprojectmanagecreateUserId;

    @TableField("FileProjectManageCreate_time")
    private Date fileprojectmanagecreateTime;

    @TableField("FileProjectManageCreate_UserName")
    private String fileprojectmanagecreateUserName;

    @TableField("FileProjectManageIsdlt")
    private Integer fileProjectManageIsdlt;

    @TableField("FileProjectManageUpdate_time")
    private Date fileprojectmanageupdateTime;

    @TableField("FileProjectManageOrgmasterId")
    private String fileProjectManageOrgmasterId;
}
