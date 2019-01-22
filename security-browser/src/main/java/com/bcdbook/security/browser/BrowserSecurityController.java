package com.bcdbook.security.browser;

import com.bcdbook.security.core.properties.SecurityConstants;
import com.bcdbook.security.core.properties.SecurityProperties;
import com.bcdbook.security.core.support.SimpleResponse;
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
    @RequestMapping(SecurityConstants.AUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 获取情趣的缓存
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            // 从请求的缓存中获取其上一个请求
            String targetUrl = savedRequest.getRedirectUrl();

            log.info("引发跳转的请求是:"+targetUrl);

            // 如果忽略大小写的情况下是以 .html 结尾, 则跳转到登录页面
            // TODO: 2019-01-21 此处想要解决是通过页面访问还是通过 json 请求的方式访问, 此方法过于简单粗暴, 后期优化
            if(StringUtils.endsWithIgnoreCase(targetUrl, ".html")){
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }

        // 如果不是, 则返回 json 格式的简单错误信息
        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }

}
