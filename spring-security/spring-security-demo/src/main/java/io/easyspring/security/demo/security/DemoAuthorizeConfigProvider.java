package io.easyspring.security.demo.security;

import io.easyspring.security.authorize.AuthorizeConfigProvider;
import io.easyspring.security.authorize.constant.SecurityAuthorizeProviderLoadOrderConstant;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 调用者实现的权限校验器
 *
 * @author summer
 * @date 2019-03-06 16:37
 * @version V1.0.0-RELEASE
 */
@Component
@Order(SecurityAuthorizeProviderLoadOrderConstant.LOAD_ORDER_TERMINAL)
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {

    /**
     * 调用者执行权限校验的配置方法
     *
     * @param config 校验配置器
     * @return void
     * @author summer
     * @date 2019-03-06 16:38
     * @version V1.0.0-RELEASE
     */
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.anyRequest()
                .access("@dynamicAuthorizePermissionService.hasPermission(request, authentication)");
    }
}
