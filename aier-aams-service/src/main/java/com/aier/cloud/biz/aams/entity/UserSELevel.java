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
import java.math.BigDecimal;
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
@TableName("UserSELevel")
@ApiModel(value = "UserSELevel对象", description = "")
public class UserSELevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "UserSELevelId", type = IdType.AUTO)
    private Long userSELevelId;

    @TableField("UserSelfEvaluationId")
    private Long userSelfEvaluationId;

    @TableField("UserSELevelCode")
    private String userSELevelCode;

    @TableField("UserSELevelScore")
    private BigDecimal userSELevelScore;

    @TableField("UserSELevelCreate_time")
    private Date userselevelcreateTime;

    @TableField("UserSELevelCreate_User")
    private String userselevelcreateUser;


}
