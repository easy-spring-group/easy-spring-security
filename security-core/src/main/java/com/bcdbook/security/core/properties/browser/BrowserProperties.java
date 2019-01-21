package com.bcdbook.security.core.properties.browser;

import com.bcdbook.security.core.properties.SignInResponseType;
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
    private String loginPage = "/easy-signIn.html";

    /**
     * 返回结果的形式, 默认为 Json
     */
    private SignInResponseType signInResponseType = SignInResponseType.JSON;

}
