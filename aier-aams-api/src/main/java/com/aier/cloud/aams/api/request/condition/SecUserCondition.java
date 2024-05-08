package com.aier.cloud.aams.api.request.condition;

import com.aier.cloud.aams.api.request.domain.SecUser;
import com.aier.cloud.basic.api.request.condition.base.PageCondition;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SecUserCondition extends PageCondition { 
    private Long secuserid;

    private String secusername;

    private String secuserpassword;

    private String secusercode;

    private Date secusercreateTime;

    private Date secuserupdateTime;

    private Boolean secuserisdlt;

    private String secusermaincode;

    private String secuserposcode;

    private String secuserleaderposcode;

    private String secuserposname;

    private String deptmastercode;

    private String secusergender;

    private String secuseremail;

    private String secuserphonenumber;

    private Short secuserlogincount;

    private Date secuserlastlogintime;

    private String secuserloginip;

    private static final long serialVersionUID = 1L;

    public Long getSecuserid() {
        return secuserid;
    }

    public void setSecuserid(Long secuserid) {
        this.secuserid = secuserid;
    }

    public String getSecusername() {
        return secusername;
    }

    public void setSecusername(String secusername) {
        this.secusername = secusername;
    }

    public String getSecuserpassword() {
        return secuserpassword;
    }

    public void setSecuserpassword(String secuserpassword) {
        this.secuserpassword = secuserpassword;
    }

    public String getSecusercode() {
        return secusercode;
    }

    public void setSecusercode(String secusercode) {
        this.secusercode = secusercode;
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

    public Boolean getSecuserisdlt() {
        return secuserisdlt;
    }

    public void setSecuserisdlt(Boolean secuserisdlt) {
        this.secuserisdlt = secuserisdlt;
    }

    public String getSecusermaincode() {
        return secusermaincode;
    }

    public void setSecusermaincode(String secusermaincode) {
        this.secusermaincode = secusermaincode;
    }

    public String getSecuserposcode() {
        return secuserposcode;
    }

    public void setSecuserposcode(String secuserposcode) {
        this.secuserposcode = secuserposcode;
    }

    public String getSecuserleaderposcode() {
        return secuserleaderposcode;
    }

    public void setSecuserleaderposcode(String secuserleaderposcode) {
        this.secuserleaderposcode = secuserleaderposcode;
    }

    public String getSecuserposname() {
        return secuserposname;
    }

    public void setSecuserposname(String secuserposname) {
        this.secuserposname = secuserposname;
    }

    public String getDeptmastercode() {
        return deptmastercode;
    }

    public void setDeptmastercode(String deptmastercode) {
        this.deptmastercode = deptmastercode;
    }

    public String getSecusergender() {
        return secusergender;
    }

    public void setSecusergender(String secusergender) {
        this.secusergender = secusergender;
    }

    public String getSecuseremail() {
        return secuseremail;
    }

    public void setSecuseremail(String secuseremail) {
        this.secuseremail = secuseremail;
    }

    public String getSecuserphonenumber() {
        return secuserphonenumber;
    }

    public void setSecuserphonenumber(String secuserphonenumber) {
        this.secuserphonenumber = secuserphonenumber;
    }

    public Short getSecuserlogincount() {
        return secuserlogincount;
    }

    public void setSecuserlogincount(Short secuserlogincount) {
        this.secuserlogincount = secuserlogincount;
    }

    public Date getSecuserlastlogintime() {
        return secuserlastlogintime;
    }

    public void setSecuserlastlogintime(Date secuserlastlogintime) {
        this.secuserlastlogintime = secuserlastlogintime;
    }

    public String getSecuserloginip() {
        return secuserloginip;
    }

    public void setSecuserloginip(String secuserloginip) {
        this.secuserloginip = secuserloginip;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SecUser other = (SecUser) that;
        return (this.getSecuserid() == null ? other.getSecuserid() == null : this.getSecuserid().equals(other.getSecuserid()))
            && (this.getSecusername() == null ? other.getSecusername() == null : this.getSecusername().equals(other.getSecusername()))
            && (this.getSecuserpassword() == null ? other.getSecuserpassword() == null : this.getSecuserpassword().equals(other.getSecuserpassword()))
            && (this.getSecusercode() == null ? other.getSecusercode() == null : this.getSecusercode().equals(other.getSecusercode()))
            && (this.getSecusercreateTime() == null ? other.getSecusercreateTime() == null : this.getSecusercreateTime().equals(other.getSecusercreateTime()))
            && (this.getSecuserupdateTime() == null ? other.getSecuserupdateTime() == null : this.getSecuserupdateTime().equals(other.getSecuserupdateTime()))
            && (this.getSecuserisdlt() == null ? other.getSecuserisdlt() == null : this.getSecuserisdlt().equals(other.getSecuserisdlt()))
            && (this.getSecusermaincode() == null ? other.getSecusermaincode() == null : this.getSecusermaincode().equals(other.getSecusermaincode()))
            && (this.getSecuserposcode() == null ? other.getSecuserposcode() == null : this.getSecuserposcode().equals(other.getSecuserposcode()))
            && (this.getSecuserleaderposcode() == null ? other.getSecuserleaderposcode() == null : this.getSecuserleaderposcode().equals(other.getSecuserleaderposcode()))
            && (this.getSecuserposname() == null ? other.getSecuserposname() == null : this.getSecuserposname().equals(other.getSecuserposname()))
            && (this.getDeptmastercode() == null ? other.getDeptmastercode() == null : this.getDeptmastercode().equals(other.getDeptmastercode()))
            && (this.getSecusergender() == null ? other.getSecusergender() == null : this.getSecusergender().equals(other.getSecusergender()))
            && (this.getSecuseremail() == null ? other.getSecuseremail() == null : this.getSecuseremail().equals(other.getSecuseremail()))
            && (this.getSecuserphonenumber() == null ? other.getSecuserphonenumber() == null : this.getSecuserphonenumber().equals(other.getSecuserphonenumber()))
            && (this.getSecuserlogincount() == null ? other.getSecuserlogincount() == null : this.getSecuserlogincount().equals(other.getSecuserlogincount()))
            && (this.getSecuserlastlogintime() == null ? other.getSecuserlastlogintime() == null : this.getSecuserlastlogintime().equals(other.getSecuserlastlogintime()))
            && (this.getSecuserloginip() == null ? other.getSecuserloginip() == null : this.getSecuserloginip().equals(other.getSecuserloginip()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSecuserid() == null) ? 0 : getSecuserid().hashCode());
        result = prime * result + ((getSecusername() == null) ? 0 : getSecusername().hashCode());
        result = prime * result + ((getSecuserpassword() == null) ? 0 : getSecuserpassword().hashCode());
        result = prime * result + ((getSecusercode() == null) ? 0 : getSecusercode().hashCode());
        result = prime * result + ((getSecusercreateTime() == null) ? 0 : getSecusercreateTime().hashCode());
        result = prime * result + ((getSecuserupdateTime() == null) ? 0 : getSecuserupdateTime().hashCode());
        result = prime * result + ((getSecuserisdlt() == null) ? 0 : getSecuserisdlt().hashCode());
        result = prime * result + ((getSecusermaincode() == null) ? 0 : getSecusermaincode().hashCode());
        result = prime * result + ((getSecuserposcode() == null) ? 0 : getSecuserposcode().hashCode());
        result = prime * result + ((getSecuserleaderposcode() == null) ? 0 : getSecuserleaderposcode().hashCode());
        result = prime * result + ((getSecuserposname() == null) ? 0 : getSecuserposname().hashCode());
        result = prime * result + ((getDeptmastercode() == null) ? 0 : getDeptmastercode().hashCode());
        result = prime * result + ((getSecusergender() == null) ? 0 : getSecusergender().hashCode());
        result = prime * result + ((getSecuseremail() == null) ? 0 : getSecuseremail().hashCode());
        result = prime * result + ((getSecuserphonenumber() == null) ? 0 : getSecuserphonenumber().hashCode());
        result = prime * result + ((getSecuserlogincount() == null) ? 0 : getSecuserlogincount().hashCode());
        result = prime * result + ((getSecuserlastlogintime() == null) ? 0 : getSecuserlastlogintime().hashCode());
        result = prime * result + ((getSecuserloginip() == null) ? 0 : getSecuserloginip().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", secuserid=").append(secuserid);
        sb.append(", secusername=").append(secusername);
        sb.append(", secuserpassword=").append(secuserpassword);
        sb.append(", secusercode=").append(secusercode);
        sb.append(", secusercreateTime=").append(secusercreateTime);
        sb.append(", secuserupdateTime=").append(secuserupdateTime);
        sb.append(", secuserisdlt=").append(secuserisdlt);
        sb.append(", secusermaincode=").append(secusermaincode);
        sb.append(", secuserposcode=").append(secuserposcode);
        sb.append(", secuserleaderposcode=").append(secuserleaderposcode);
        sb.append(", secuserposname=").append(secuserposname);
        sb.append(", deptmastercode=").append(deptmastercode);
        sb.append(", secusergender=").append(secusergender);
        sb.append(", secuseremail=").append(secuseremail);
        sb.append(", secuserphonenumber=").append(secuserphonenumber);
        sb.append(", secuserlogincount=").append(secuserlogincount);
        sb.append(", secuserlastlogintime=").append(secuserlastlogintime);
        sb.append(", secuserloginip=").append(secuserloginip);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}