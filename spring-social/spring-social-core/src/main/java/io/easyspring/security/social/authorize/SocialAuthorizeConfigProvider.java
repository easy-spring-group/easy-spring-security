package io.easyspring.security.social.authorize;

import io.easyspring.security.authorize.AuthorizeConfigProvider;
import io.easyspring.security.authorize.properties.SecurityAuthorizeProviderLoadOrderConstant;
import io.easyspring.security.social.properties.SocialProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 社交登录的权限配置
 *
 * @author summer
 * @date 2019-03-06 10:34
 * @version V1.0.0-RELEASE
 */
@Component
@Order(SecurityAuthorizeProviderLoadOrderConstant.LOAD_ORDER_SOCIAL)
public class SocialAuthorizeConfigProvider implements AuthorizeConfigProvider {
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
                // 授权完成后若没有用户信息, 所要跳转的页面
                socialProperties.getSignUpUrl())
                .permitAll();
    }
}
