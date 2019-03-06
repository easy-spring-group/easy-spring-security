package io.easyspring.security.core.authorize;


import io.easyspring.security.authorize.AuthorizeConfigProvider;
import io.easyspring.security.core.properties.SecurityConstants;
import io.easyspring.security.core.properties.SecurityProperties;
import io.easyspring.security.social.properties.SocialProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;


/**
 * 默认的权限控制实现
 *
 * @author summer
 * @date 2019-03-05 16:19
 * @version V1.0.0-RELEASE
 */
@Component
@Order(Integer.MIN_VALUE)
public class EasyAuthorizeConfigProvider implements AuthorizeConfigProvider {

    /**
     * 注入 Security 的配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 注入社交登录的配置
     */
    @Autowired
    private SocialProperties socialProperties;

    /**
     * 配置忽略的权限校验接口
     *
     * @param config 权限校验配置对象
     * @return void
     * @author summer
     * @date 2019-03-05 16:17
     * @version V1.0.0-RELEASE
     */
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        // 请求拦截时, 忽略一下路径
        config.antMatchers(
                // 未授权的时候的跳转的页面
                SecurityConstants.SignIn.DEFAULT_AUTHENTICATION_URL,
                // 手机验证码登录的接口
                SecurityConstants.SignIn.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                // 验证码接口,
                SecurityConstants.Validate.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                // 普通登录页面
                securityProperties.getBrowser().getLoginPage(),
                // 授权完成后若没有用户信息, 所要跳转的页面
                // TODO: 2019-03-05 待处理
                socialProperties.getSignUpUrl(),
                // session 失效后跳转的地址
                securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                // 退出登录成功后跳转的地址
                securityProperties.getBrowser().getSignOut().getSignOutSuccessUrl())
                .permitAll();
    }

}
