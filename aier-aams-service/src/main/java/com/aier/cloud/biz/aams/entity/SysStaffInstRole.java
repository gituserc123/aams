package com.aier.cloud.biz.aams.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2024-05-08 09:50:12
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("T_SYS_STAFF_INST_ROLE")
@ApiModel(value = "SysStaffInstRole对象", description = "")
public class SysStaffInstRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("role_id")
    private Long roleId;

    @TableField("institution_id")
    private Long institutionId;

    @TableField("create_date")
    private Date createDate;

    @TableField("modify_date")
    private Date modifyDate;

    @TableField("staff_id")
    private Long staffId;

    @TableField("creator")
    private Long creator;

    @TableField("modifer")
    private Long modifer;


}
