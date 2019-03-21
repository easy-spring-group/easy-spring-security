package io.easyspring.security.core.properties.browser;

import io.easyspring.security.core.properties.SecurityConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Session 的配置
 *
 * @author summer
 * DateTime 2019-02-18 18:51
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
public class SessionProperties {

    /**
     * 最大允许的 session 数量
     */
    private int maximumSessions = 1;
    /**
     * 当 session 达到最大值后, 是否是阻止下一个登录, 默认值(false)
     */
    private boolean maxSessionsPreventsLogin;
    /**
     * session 失效后跳转的地址
     */
    private String sessionInvalidUrl = SecurityConstants.SignIn.DEFAULT_SESSION_INVALID_URL;

}
