package com.aier.cloud.aams.api.domain.enums;

import com.aier.cloud.basic.common.convert.EnumDict;

public enum OrgCapabilityEnum  implements EnumDict<OrgCapabilityEnum> {

    Outpatient(1,"Outpatient","视光门诊"),

    County(2,"County","县级"),

    NewHosp(4,"NewHosp","新院"),

    Small(8,"Small","小体量"),

    Medium(16,"Medium","中体量"),

    Big(32,"Big","大体量"),

    ;


    private Integer value;

    private String code;

    private String content;

    OrgCapabilityEnum(Integer value, String code, String content) {
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
