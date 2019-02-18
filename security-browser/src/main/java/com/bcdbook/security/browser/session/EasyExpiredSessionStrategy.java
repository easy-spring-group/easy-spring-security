package com.bcdbook.security.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;


/**
 * 自定义 session 的过期策略
 *
 * @author summer
 * @date 2019-02-18 20:30
 * @version V1.0.0-RELEASE
 */
public class EasyExpiredSessionStrategy
        extends AbstractSessionStrategy
        implements SessionInformationExpiredStrategy {

    /**
     * 构造方法
     *
     * @author summer
     * @date 2019-02-18 20:37
     * @param invalidSessionUrl 超时后跳转的地址
     * @version V1.0.0-RELEASE
     */
    public EasyExpiredSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    /**
     * 覆盖 session 过期的事件
     * @see org.springframework.security.web.session.SessionInformationExpiredStrategy#onExpiredSessionDetected(
     * org.springframework.security.web.session.SessionInformationExpiredEvent)
     *
     * @author summer
     * @date 2019-02-18 20:31
     * @param event session 回话过期事件
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    /**
     * 处理重定向地址的方法
     * @see com.bcdbook.security.browser.session.AbstractSessionStrategy#processRedirectUrl(java.lang.String)
     *
     * @author summer
     * @date 2019-02-18 20:32
     * @param targetUrl 重定向的地址
     * @return java.lang.String
     * @version V1.0.0-RELEASE
     */
    @Override
    protected String processRedirectUrl(String targetUrl) {
        return targetUrl+"?concurrency=true";
    }

}
