package com.bcdbook.security.browser;

import com.bcdbook.security.core.EasyAuthenticationException;
import com.bcdbook.security.core.properties.SecurityConstants;
import com.bcdbook.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 浏览器 security 接口
 *
 * @author summer
 * @date 2019-01-22 20:37
 * @version V1.0.0-RELEASE
 */
@RestController
@Slf4j
public class BrowserSecurityController {

    /**
     * 获取请求缓存器
     */
    private RequestCache requestCache = new HttpSessionRequestCache();
    /**
     * 重定向的工具类
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 注入 security 的配置类
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当需要身份认证时，跳转到这里
     *
     * @author summer
     * @date 2019-01-21 16:54
     * @param request 请求对象
     * @param response 返回对象
     * @annotation @ResponseStatus 设置当前请求返回时返回的状态码
     * @return com.bcdbook.security.browser.support.SimpleResponse
     * @version V1.0.0-RELEASE
     */
    @RequestMapping(SecurityConstants.Signin.AUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public void requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 获取请求的缓存
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        // 定于引发跳转的请求地址
        String targetUrl = "";
        // 如果缓存的跳转请求不为空
        if (savedRequest != null) {
            // 从请求的缓存中获取其上一个请求
            targetUrl = savedRequest.getRedirectUrl();
        }

        log.info("引发跳转的请求是:"+targetUrl);

        // 检查是否是 html 请求
        boolean isHtml = isHtml(request);
        // 检查是否以 .html 结尾
        boolean endsWithHtml = StringUtils.endsWithIgnoreCase(targetUrl, ".html");
        // 如果都不是
        if (!isHtml && !endsWithHtml) {
            throw new EasyAuthenticationException("访问的服务需要身份认证，请引导用户到登录页");
        }
        redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
    }

    /**
     * 校验当前请求是否是 ajax 请求
     *
     * @author summer
     * @date 2019-01-07 17:34
     * @param request 请求对象
     * @return boolean
     * @version V1.0.0-RELEASE
     */
    private static boolean isHtml(HttpServletRequest request){
        // 传入参数校验
        if (request == null) {
            return false;
        }

        // 从请求头中获取 内容类型
        String accept = request.getHeader("Accept");
        // 如果请求内容不为空, 并且包含 json 则说明是 ajax 请求
        return !StringUtils.isEmpty(accept) && accept.contains("text/html");
    }
}
