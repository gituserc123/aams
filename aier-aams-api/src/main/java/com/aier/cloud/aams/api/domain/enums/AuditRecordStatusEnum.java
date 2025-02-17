package com.aier.cloud.aams.api.domain.enums;

import com.aier.cloud.basic.common.convert.EnumDict;

public enum AuditRecordStatusEnum implements EnumDict<AuditRecordStatusEnum> {

    INIT(0, "init", "初始化"),
    AGREE(1, "agree", "同意"),
    SUBMIT(2, "submit", "提交"),
    REVIEW(3, "review", "复核"),
    REJECT(4, "reject", "拒绝"),
    SUBMIT2(5, "submit2", "暂时提交"),
    NOPUBLIC(6, "nopublic", "不公开");

    private Integer value;
    private String code;
    private String content;

    AuditRecordStatusEnum(Integer value, String code, String content) {
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
        return code;
    }

    @Override
    public String getEnumDesc() {
        return content;
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
