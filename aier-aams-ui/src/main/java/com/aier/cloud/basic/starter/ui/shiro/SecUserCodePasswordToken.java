package com.aier.cloud.basic.starter.ui.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 外部系统 iframe 免登录 Token
 * 携带 Genexus SecUser 的 userCode + MD5密码（外部系统已加密），供 ShiroDbRealm 识别并走独立认证分支
 */
public class SecUserCodePasswordToken implements AuthenticationToken {

    private final String secUserCode;
    private final String secUserPassword; // 外部系统传入的 MD5 密文

    public SecUserCodePasswordToken(String secUserCode, String secUserPassword) {
        this.secUserCode = secUserCode;
        this.secUserPassword = secUserPassword;
    }

    @Override
    public Object getPrincipal() {
        return secUserCode;
    }

    @Override
    public Object getCredentials() {
        return secUserPassword;
    }

    public String getSecUserCode() {
        return secUserCode;
    }

    public String getSecUserPassword() {
        return secUserPassword;
    }
}
