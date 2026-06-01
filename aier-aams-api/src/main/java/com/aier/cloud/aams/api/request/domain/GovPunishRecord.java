package com.aier.cloud.aams.api.request.domain;

import com.aier.cloud.basic.api.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class GovPunishRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long govPunishRecordId;
    private String govPunishRecordBizId;
    private String govPunishRecordShengQu;
    private String govPunishRecordUserCode;
    private String govPunishRecordLoadDate;
    private Date govpunishrecordcreateTime;
    private String govPunishRecordAi;
    private String govpunishrecordpunishAmt;
    private String govpunishrecordpunishDate;
    private String govpunishrecordstandardId;
}
