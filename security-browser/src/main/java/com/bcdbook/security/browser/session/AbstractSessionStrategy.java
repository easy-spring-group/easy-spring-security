package com.bcdbook.security.browser.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session 失效后的抽象处理类
 *
 * @author summer
 * @date 2019-02-18 20:45
 * @version V1.0.0-RELEASE
 */
@Slf4j
public class AbstractSessionStrategy {

    /**
     * 跳转的url
     */
    private String destinationUrl;
    /**
     * 重定向策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    /**
     * 跳转前是否创建新的session
     */
    private boolean createNewSession = true;

    /**
     * 构造方法
     *
     * @author summer
     * @date 2019-02-18 18:58
     * @param invalidSessionUrl session 过期后处理的地址
     * @version V1.0.0-RELEASE
     */
    public AbstractSessionStrategy(String invalidSessionUrl) {
        // 断言这是一个地址, 否则, 抛出异常信息
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl),
                "url must start with '/' or with 'http(s)'");

        // 如果是合法的请求地址, 则设置跳转的地址为传入的地址
        this.destinationUrl = invalidSessionUrl;
    }

    /**
     * 当 session 失效后的处理
     * @see org.springframework.security.web.session.InvalidSessionStrategy#onInvalidSessionDetected(
     * javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
     *
     * @author summer
     * @date 2019-02-18 20:34
     * @param request 请求信息
     * @param response 返回信息
     * @return void
     * @version V1.0.0-RELEASE
     */
    protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 如果需要创建信息的 session
        if (createNewSession) {
            request.getSession();
        }

        // 获取源地址
        String sourceUrl = request.getRequestURI();
        // 定义目标地址为 session 失败后处理的地址
        String targetUrl = destinationUrl;

        // 如果请求地址是以 .html 结尾的, 则需要封装目标地址为 .html 的形式
        // TODO: 2019-02-18 需要优化
        if (StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
            targetUrl = destinationUrl + ".html";
        }

        log.info("session失效,跳转到" + targetUrl);

        // 封装重定向地址
        targetUrl = processRedirectUrl(targetUrl);

        // 执行地址的重定向
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * 封装重定向地址的方法
     *
     * @author summer
     * @date 2019-02-18 20:41
     * @param targetUrl 原始目标地址
     * @return java.lang.String
     * @version V1.0.0-RELEASE
     */
    protected String processRedirectUrl(String targetUrl) {
        return targetUrl;
    }

    /**
     * Determines whether a new session should be created before redirecting (to
     * avoid possible looping issues where the same session ID is sent with the
     * redirected request). Alternatively, ensure that the configured URL does
     * not pass through the {@code SessionManagementFilter}.
     *
     * @param createNewSession
     *            defaults to {@code true}.
     */
    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }

}
