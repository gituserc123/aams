package com.aier.cloud.aams.api.domain.enums;

import com.aier.cloud.basic.common.convert.EnumDict;

public enum ForHospTypeEnum implements EnumDict<ForHospTypeEnum> {

    Hospital(1,"Hospital","医院"),

    OutPatient(2,"OutPatient","眼科门诊"),

    EyeOpticClinic(4,"EyeOpticClinic","眼视光诊所"),

    SpectacleStore(8,"SpectacleStore","眼镜店"),

    ;


    private Integer value;

    private String code;

    private String content;

    ForHospTypeEnum(Integer value, String code, String content) {
        this.value = value;
        this.code = code;
        this.content = content;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getEnumCode() {
        return null;
    }

    @Override
    public String getEnumDesc() {
        return null;
    }

    @Override
    public String getFirstSpell() {
        return EnumDict.super.getFirstSpell();
    }

    @Override
    public boolean is(Object o) {
        return EnumDict.super.is(o);
    }
}
