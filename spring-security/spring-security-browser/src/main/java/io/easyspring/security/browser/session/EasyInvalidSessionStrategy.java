package io.easyspring.security.browser.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session 无效的策略
 *
 * @author summer
 * @date 2019-02-18 20:49
 * @version V1.0.0-RELEASE
 */
public class EasyInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

    /**
     * 构造方法
     *
     * @author summer
     * @date 2019-02-18 20:49
     * @param invalidSessionUrl session 出现问题后跳转的地址
     * @version V1.0.0-RELEASE
     */
    public EasyInvalidSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    /**
     * 当 session 失效后处理的方法
     *
     * @author summer
     * @date 2019-02-18 20:50
     * @param request 请求信息
     * @param response 返回信息
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        onSessionInvalid(request, response);
    }

}
