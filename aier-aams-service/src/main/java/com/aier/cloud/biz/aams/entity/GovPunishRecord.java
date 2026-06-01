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
@TableName("GovPunishRecord")
@ApiModel(value = "GovPunishRecord对象", description = "")
public class GovPunishRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "GovPunishRecordId", type = IdType.AUTO)
    private Long govPunishRecordId;

    @TableField("GovPunishRecordBizId")
    private String govPunishRecordBizId;

    @TableField("GovPunishRecordShengQu")
    private String govPunishRecordShengQu;

    @TableField("GovPunishRecordUserCode")
    private String govPunishRecordUserCode;

    @TableField("GovPunishRecordLoadDate")
    private String govPunishRecordLoadDate;

    @TableField("GovPunishRecordCreate_time")
    private Date govpunishrecordcreateTime;

    @TableField("GovPunishRecordAi")
    private String govPunishRecordAi;

    @TableField("GovPunishRecordPunish_amt")
    private String govpunishrecordpunishAmt;

    @TableField("GovPunishRecordPunish_date")
    private String govpunishrecordpunishDate;

    @TableField("GovPunishRecordStandard_id")
    private String govpunishrecordstandardId;
}
