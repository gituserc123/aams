package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import java.util.Date;

/**
 * @author 
 */
public class SecUserCondition extends PageCondition {
    private Long secUserId;

    private String secUserName;

    private String secUserPassword;

    private String secUserCode;

    private Date secusercreateTime;

    private Date secuserupdateTime;

    private Boolean secUserIsdlt;

    private String secUserMainCode;

    private String secUserPosCode;

    private String secUserLeaderPosCode;

    private String secUserPosName;

    private String deptMasterCode;

    private String secUserGender;

    private String secUserEmail;

    private String secUserPhoneNumber;

    private Integer secUserLoginCount;

    private Date secUserLastLoginTime;

    private String secUserLoginIp;

    private static final long serialVersionUID = 1L;

    public Long getSecUserId() {
        return secUserId;
    }

    public void setSecUserId(Long secUserId) {
        this.secUserId = secUserId;
    }

    public String getSecUserName() {
        return secUserName;
    }

    public void setSecUserName(String secUserName) {
        this.secUserName = secUserName;
    }

    public String getSecUserPassword() {
        return secUserPassword;
    }

    public void setSecUserPassword(String secUserPassword) {
        this.secUserPassword = secUserPassword;
    }

    public String getSecUserCode() {
        return secUserCode;
    }

    public void setSecUserCode(String secUserCode) {
        this.secUserCode = secUserCode;
    }

    public Date getSecusercreateTime() {
        return secusercreateTime;
    }

    public void setSecusercreateTime(Date secusercreateTime) {
        this.secusercreateTime = secusercreateTime;
    }

    public Date getSecuserupdateTime() {
        return secuserupdateTime;
    }

    public void setSecuserupdateTime(Date secuserupdateTime) {
        this.secuserupdateTime = secuserupdateTime;
    }

    public Boolean getSecUserIsdlt() {
        return secUserIsdlt;
    }

    public void setSecUserIsdlt(Boolean secUserIsdlt) {
        this.secUserIsdlt = secUserIsdlt;
    }

    public String getSecUserMainCode() {
        return secUserMainCode;
    }

    public void setSecUserMainCode(String secUserMainCode) {
        this.secUserMainCode = secUserMainCode;
    }

    public String getSecUserPosCode() {
        return secUserPosCode;
    }

    public void setSecUserPosCode(String secUserPosCode) {
        this.secUserPosCode = secUserPosCode;
    }

    public String getSecUserLeaderPosCode() {
        return secUserLeaderPosCode;
    }

    public void setSecUserLeaderPosCode(String secUserLeaderPosCode) {
        this.secUserLeaderPosCode = secUserLeaderPosCode;
    }

    public String getSecUserPosName() {
        return secUserPosName;
    }

    public void setSecUserPosName(String secUserPosName) {
        this.secUserPosName = secUserPosName;
    }

    public String getDeptMasterCode() {
        return deptMasterCode;
    }

    public void setDeptMasterCode(String deptMasterCode) {
        this.deptMasterCode = deptMasterCode;
    }

    public String getSecUserGender() {
        return secUserGender;
    }

    public void setSecUserGender(String secUserGender) {
        this.secUserGender = secUserGender;
    }

    public String getSecUserEmail() {
        return secUserEmail;
    }

    public void setSecUserEmail(String secUserEmail) {
        this.secUserEmail = secUserEmail;
    }

    public String getSecUserPhoneNumber() {
        return secUserPhoneNumber;
    }

    public void setSecUserPhoneNumber(String secUserPhoneNumber) {
        this.secUserPhoneNumber = secUserPhoneNumber;
    }

    public Integer getSecUserLoginCount() {
        return secUserLoginCount;
    }

    public void setSecUserLoginCount(Integer secUserLoginCount) {
        this.secUserLoginCount = secUserLoginCount;
    }

    public Date getSecUserLastLoginTime() {
        return secUserLastLoginTime;
    }

    public void setSecUserLastLoginTime(Date secUserLastLoginTime) {
        this.secUserLastLoginTime = secUserLastLoginTime;
    }

    public String getSecUserLoginIp() {
        return secUserLoginIp;
    }

    public void setSecUserLoginIp(String secUserLoginIp) {
        this.secUserLoginIp = secUserLoginIp;
    }
}