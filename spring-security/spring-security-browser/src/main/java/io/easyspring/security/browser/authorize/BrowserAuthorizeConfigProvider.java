package io.easyspring.security.browser.authorize;

import io.easyspring.security.authorize.AuthorizeConfigProvider;
import io.easyspring.security.authorize.constant.SecurityAuthorizeProviderLoadOrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;


/**
 * 浏览器的权限配置器
 *
 * @author summer
 * @date 2019-03-06 16:59
 * @version V1.0.0-RELEASE
 */
@Component
@Order(SecurityAuthorizeProviderLoadOrderConstant.LOAD_ORDER_SERVER)
@Slf4j
public class BrowserAuthorizeConfigProvider implements AuthorizeConfigProvider {

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
        config.antMatchers(HttpMethod.GET,
                "/**/*.js",
                "/**/*.css",
                "/**/*.ico",
                "/**/*.jpg",
                "/**/*.png",
                "/**/*.gif")
                .permitAll();

        log.info("SocialAuthorizeConfigProvider ignoreAuthorizes: {}",
                "/**/*.js, " +
                "/**/*.css, " +
                "/**/*.ico, " +
                "/**/*.jpg, " +
                "/**/*.png, " +
                "/**/*.gif");
    }
}
