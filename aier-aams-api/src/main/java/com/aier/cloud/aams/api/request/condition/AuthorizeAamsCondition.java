package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;

import java.util.List;

public class AuthorizeAamsCondition extends PageCondition {
    private static final long serialVersionUID = 5277109777013530350L;
    private Long roleId;
    private Long institution;
    private Long dept;
    private String keyword;
    private String platformCode;
    private String usingSign;

    private List<Long> staffIds;

    public AuthorizeAamsCondition() {
    }

    public String getPlatformCode() {
        return this.platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getInstitution() {
        return this.institution;
    }

    public void setInstitution(Long institution) {
        this.institution = institution;
    }

    public Long getDept() {
        return this.dept;
    }

    public void setDept(Long dept) {
        this.dept = dept;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUsingSign() {
        return this.usingSign;
    }

    public void setUsingSign(String usingSign) {
        this.usingSign = usingSign;
    }

    public List<Long> getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(List<Long> staffIds) {
        this.staffIds = staffIds;
    }
}
