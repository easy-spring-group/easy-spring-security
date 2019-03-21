package io.easyspring.security.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;


/**
 * 自定义 session 的过期策略
 *
 * @author summer
 * DateTime 2019-02-18 20:30
 * @version V1.0.0-RELEASE
 */
public class EasyExpiredSessionStrategy
        extends AbstractSessionStrategy
        implements SessionInformationExpiredStrategy {

    /**
     * 构造方法
     *
     * Author summer
     * DateTime 2019-02-18 20:37
     * @param invalidSessionUrl 超时后跳转的地址
     * Version V1.0.0-RELEASE
     */
    public EasyExpiredSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    /**
     * 覆盖 session 过期的事件
     * @see org.springframework.security.web.session.SessionInformationExpiredStrategy#onExpiredSessionDetected(
     * org.springframework.security.web.session.SessionInformationExpiredEvent)
     *
     * Author summer
     * DateTime 2019-02-18 20:31
     * @param event session 回话过期事件
     * Version V1.0.0-RELEASE
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    /**
     * 判断是否是并发引起的 session 失效问题
     * @see AbstractSessionStrategy#isConcurrency()
     *
     * Author summer
     * DateTime 2019-02-19 10:23
     * @return boolean
     * Version V1.0.0-RELEASE
     */
    @Override
    protected boolean isConcurrency() {
        return true;
    }
}
