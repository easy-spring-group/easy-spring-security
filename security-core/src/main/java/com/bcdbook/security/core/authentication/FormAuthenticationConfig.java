package com.bcdbook.security.core.authentication;

import com.bcdbook.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 表单登录的配置
 *
 * @author summer
 * @date 2019-01-22 14:42
 * @version V1.0.0-RELEASE
 */
@Component
public class FormAuthenticationConfig {

    /**
     * 自定义的登录成功处理器
     */
    @Autowired
    protected AuthenticationSuccessHandler easyAuthenticationSuccessHandler;
    /**
     * 自定义的登录失败处理器
     */
    @Autowired
    protected AuthenticationFailureHandler easyAuthenticationFailureHandler;

    /**
     * 表单登录的配置方法
     *
     * @author summer
     * @date 2019-01-22 14:54
     * @param http 请求信息
     * @return void
     * @version V1.0.0-RELEASE
     */
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 登录页
                .loginPage(SecurityConstants.AUTHENTICATION_URL)
                // 用户登录的接口, SpringSecurity 会监听这个接口, 当有 post 请求时, Security 会执行登录逻辑(不需要我们自己实现)
                .loginProcessingUrl(SecurityConstants.SIGN_IN_PROCESSING_URL_FORM)
                // 设置登录成功的处理拦截器
                .successHandler(easyAuthenticationSuccessHandler)
                // 等失败的拦截器
                .failureHandler(easyAuthenticationFailureHandler);
    }

}

