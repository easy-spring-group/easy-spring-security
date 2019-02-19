package com.bcdbook.security.browser.session;

import com.bcdbook.security.browser.support.SimpleResponse;
import com.bcdbook.security.core.utils.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
     * 定义 json 的工具类
     */
    private ObjectMapper objectMapper = new ObjectMapper();


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

        // 定义目标地址为 session 失败后处理的地址
        String targetUrl;

        // 校验请求是否是 html 请求
        boolean isHtml = RequestUtils.isHtml(request);

        // 如果请求地址是 html 请求
        if (isHtml) {
            targetUrl = destinationUrl;
            log.info("session失效,跳转到"+targetUrl);
            redirectStrategy.sendRedirect(request, response, targetUrl);

        } else {
            // 获取返回信息
            Object result = buildResponseContent(request);
            // 设置状态码
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            // 设置返回类型
            response.setContentType("application/json;charset=UTF-8");
            // 执行输出
            response.getWriter().write(objectMapper.writeValueAsString(result));

        }
    }

    /**
     * 封装 session 过期的返回信息
     *
     * @author summer
     * @date 2019-02-19 10:30
     * @param request 请求信息
     * @return java.lang.Object
     * @version V1.0.0-RELEASE
     */
    protected Object buildResponseContent(HttpServletRequest request) {
        String message = "session已失效";
        if(isConcurrency()){
            message = message + "，有可能是并发登录导致的";
        }
        return new SimpleResponse(message);
    }


    /**
     * 判断是否是并发引起的 session 失效问题
     *
     * @author summer
     * @date 2019-02-19 10:23
     * @return boolean
     * @version V1.0.0-RELEASE
     */
    protected boolean isConcurrency() {
        return false;
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
