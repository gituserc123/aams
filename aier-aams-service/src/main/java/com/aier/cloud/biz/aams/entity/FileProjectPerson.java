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
@TableName("FileProjectPerson")
@ApiModel(value = "FileProjectPerson对象", description = "")
public class FileProjectPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "FileProjectPersonId", type = IdType.AUTO)
    private Long fileProjectPersonId;

    @TableField("FileProjectManageId")
    private Long fileProjectManageId;

    @TableField("FileProjectPersonUserId")
    private Long fileProjectPersonUserId;

    @TableField("FileProjectPersonUserName")
    private String fileProjectPersonUserName;

    @TableField("FileProjectPersonUserCode")
    private String fileProjectPersonUserCode;

    @TableField("FileProjectPersonUserMainCode")
    private String fileProjectPersonUserMainCode;

    @TableField("FileProjectPersonOrgMasterId")
    private String fileProjectPersonOrgMasterId;

    @TableField("FileProjectPersonUserType")
    private String fileProjectPersonUserType;

    @TableField("FileProjectPersonCreate_UserId")
    private Long fileprojectpersoncreateUserId;

    @TableField("FileProjectPersonCreate_time")
    private Date fileprojectpersoncreateTime;
}
