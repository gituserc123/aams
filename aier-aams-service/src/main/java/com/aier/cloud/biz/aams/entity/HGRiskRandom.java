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
 * @since 2024-04-20 11:52:58
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("HGRiskRandom")
@ApiModel(value = "HGRiskRandom对象", description = "")
public class HGRiskRandom implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "HGRiskRandomBussinessType", type = IdType.AUTO)
    private String hGRiskRandomBussinessType;

    @TableField("HGRiskRandomUpdate_time")
    private Date hgriskrandomupdateTime;

    @TableField("HGRiskRandomUpdate_user")
    private String hgriskrandomupdateUser;

    @TableField("HGRiskRandomNum")
    private Integer hGRiskRandomNum;


}
