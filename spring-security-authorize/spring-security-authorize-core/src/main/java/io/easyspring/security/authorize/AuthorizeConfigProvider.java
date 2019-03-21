package io.easyspring.security.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 自定义权限控制接口, 用于配置默认忽略的路径
 *
 * @author summer
 * DateTime 2019-03-05 16:17
 * @version V1.0.0-RELEASE
 */
public interface AuthorizeConfigProvider {

    /**
     * 配置忽略的权限校验接口
     *
     * @see HttpSecurity#authorizeRequests()
     * @param config 权限校验配置对象
     * Author summer
     * DateTime 2019-03-05 16:17
     * Version V1.0.0-RELEASE
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
