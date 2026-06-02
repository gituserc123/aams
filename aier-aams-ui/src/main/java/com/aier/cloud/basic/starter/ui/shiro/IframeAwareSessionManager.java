package com.aier.cloud.basic.starter.ui.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 扩展 DefaultWebSessionManager，支持从 URL 参数 _sid 读取 SessionId。
 * 取值优先级：
 *   1. 当前请求 URL 的 _sid 参数（首次跳转）
 *   2. Referer 头里的 _sid 参数（pop 弹窗、子页面等二次请求）
 *   3. 父类默认 Cookie 逻辑（正常浏览器登录，不受影响）
 */
public class IframeAwareSessionManager extends DefaultWebSessionManager {

    private static final Logger log = LoggerFactory.getLogger(IframeAwareSessionManager.class);

    /** iframe 免登录传递 SessionId 的 URL 参数名 */
    public static final String SID_PARAM = "_sid";

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        javax.servlet.http.HttpServletRequest httpReq = WebUtils.toHttp(request);

        // 1. 当前 URL 有 _sid（首次跳转落地）
        String sid = httpReq.getParameter(SID_PARAM);
        if (StringUtils.isNotBlank(sid)) {
            log.debug("[IframeSession] 从当前 URL 读取 SessionId: {}", sid);
            markUrlSession(request, sid);
            return sid;
        }

        // 2. 当前 URL 没有 _sid，尝试从 Referer 中提取
        //    场景：$pop.popTemForm / $pop.iframePop 打开子页面，
        //          Referer = 父页面 URL（包含 _sid=xxx）
        String referer = httpReq.getHeader("Referer");
        if (StringUtils.isNotBlank(referer)) {
            String sidFromReferer = extractParam(referer, SID_PARAM);
            if (StringUtils.isNotBlank(sidFromReferer)) {
                log.debug("[IframeSession] 从 Referer 读取 SessionId: {}, referer={}", sidFromReferer, referer);
                markUrlSession(request, sidFromReferer);
                return sidFromReferer;
            }
        }

        // 3. 正常请求走 Cookie（正常登录路径，完全不受影响）
        return super.getSessionId(request, response);
    }

    /** 告知 Shiro 本次 Session 来源是 URL */
    private void markUrlSession(ServletRequest request, String sid) {
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sid);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
    }

    /**
     * 从完整 URL 字符串中提取指定参数值
     * 例如 extractParam("http://host/path?foo=1&_sid=abc&bar=2", "_sid") => "abc"
     */
    private String extractParam(String url, String paramName) {
        // 取 ? 后面的 query string
        int qIdx = url.indexOf('?');
        if (qIdx < 0) return null;
        String query = url.substring(qIdx + 1);
        // 去掉锚点
        int hashIdx = query.indexOf('#');
        if (hashIdx >= 0) query = query.substring(0, hashIdx);

        for (String pair : query.split("&")) {
            int eqIdx = pair.indexOf('=');
            if (eqIdx < 0) continue;
            try {
                String key = URLDecoder.decode(pair.substring(0, eqIdx), "UTF-8");
                if (paramName.equals(key)) {
                    return URLDecoder.decode(pair.substring(eqIdx + 1), "UTF-8");
                }
            } catch (UnsupportedEncodingException ignored) {
            }
        }
        return null;
    }
}
