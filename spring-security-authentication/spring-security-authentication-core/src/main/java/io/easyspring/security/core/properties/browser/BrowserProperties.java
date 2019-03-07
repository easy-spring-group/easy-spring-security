package io.easyspring.security.core.properties.browser;

import io.easyspring.security.core.properties.SecurityConstants;
import io.easyspring.security.core.properties.SignInResponseType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 浏览器环境配置项
 *
 * @author summer
 * @date 2019-01-16 20:34
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
public class BrowserProperties {

    /**
     * 登录页面，当引发登录行为的 url 以 html 或请求类型为 text/html 时，
     * 会跳到这里配置的 url 上
     */
    private String loginPage = SecurityConstants.SignIn.DEFAULT_SIGN_IN_PAGE_URL;
    /**
     * 退出登录时, 请求的地址
     */
    private String signOutUrl = SecurityConstants.SignIn.DEFAULT_SIGN_OUT_URL;
    /**
     * 登录成功后跳转的页面
     */
    private String singInSuccessUrl;
    /**
     * 退出登录时的相关配置
     */
    private SignOutProperties signOut = new SignOutProperties();

    /**
     * 返回结果的形式, 默认为 Json
     */
    private SignInResponseType signInResponseType = SignInResponseType.JSON;

    /**
     * 记住我功能的记住时长
     */
    private int rememberMeSeconds = 3600;

    /**
     * session  的相关配置
     */
    private SessionProperties session = new SessionProperties();

}
