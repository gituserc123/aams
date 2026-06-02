package com.aier.cloud.basic.starter.ui.controller;

import com.aier.cloud.basic.starter.ui.shiro.IframeAwareSessionManager;
import com.aier.cloud.basic.starter.ui.shiro.SecUserCodePasswordToken;
import com.aier.cloud.basic.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 外部系统 iframe 免登录跳转
 * 接收外部传入的 SecUserCode + SecUserPassword（MD5密文），验证通过后建立 Shiro Session 并跳转目标页
 */
@Controller
@RequestMapping("/iframeLogin")
public class IframeLoginController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(IframeLoginController.class);

    /** 只允许跳转到本系统内部路径，防止 Open Redirect 攻击 */
    private static final String ALLOWED_REDIRECT_PREFIX = "/ui/aams/";
    private static final String ALLOWED_REDIRECT_PREFIX_ABS = "/ui/aams/";

    private boolean isSafeRedirect(String url) {
        if (StringUtils.isBlank(url)) return false;
        // 相对路径
        if (url.startsWith(ALLOWED_REDIRECT_PREFIX)) return true;
        // 绝对路径：去掉 http(s)://host:port 后校验路径前缀
        try {
            java.net.URI uri = new java.net.URI(url);
            String path = uri.getPath();
            return path != null && path.startsWith(ALLOWED_REDIRECT_PREFIX_ABS);
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/auth")
    public void auth(
            @RequestParam("secUserCode")     String secUserCode,
            @RequestParam("secUserPassword") String secUserPassword,
            @RequestParam("redirectUrl")     String redirectUrl,
            HttpServletResponse response) throws IOException {

        // redirectUrl 安全校验：只允许本系统内部路径
        if (!isSafeRedirect(redirectUrl)) {
            LOG.warn("[IframeLogin] 非法 redirectUrl: {}", redirectUrl);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "非法的跳转地址");
            return;
        }

        if (StringUtils.isBlank(secUserCode) || StringUtils.isBlank(secUserPassword)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "用户名或密码不能为空");
            return;
        }

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new SecUserCodePasswordToken(secUserCode, secUserPassword));
        } catch (AuthenticationException e) {
            LOG.warn("[IframeLogin] 认证失败, secUserCode={}, reason={}", secUserCode, e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "认证失败：" + e.getMessage());
            return;
        }

        LOG.info("[IframeLogin] 认证成功, secUserCode={}, redirect={}", secUserCode, redirectUrl);

        // 获取刚建立的 SessionId，拼入 redirectUrl
        // 目的：iframe 跨站场景下浏览器不带 Cookie，用 URL 传递 SessionId
        Session session = subject.getSession(false);
        if (session != null) {
            String sid = String.valueOf(session.getId());
            String separator = redirectUrl.contains("?") ? "&" : "?";
            redirectUrl = redirectUrl + separator + IframeAwareSessionManager.SID_PARAM + "=" + sid;
        }

        response.sendRedirect(redirectUrl);
    }
}

