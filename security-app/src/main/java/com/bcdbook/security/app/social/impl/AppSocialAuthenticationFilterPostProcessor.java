package com.bcdbook.security.app.social.impl;

import com.bcdbook.security.social.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 社交登录的后处理器
 * TODO 此实现之后需要提取到 具体的使用者中
 *
 * @author summer
 * @date 2019-02-20 20:33
 * @version V1.0.0-RELEASE
 */
@Component
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterPostProcessor {

    /**
     * 注入登录成功的处理器
     */
    @Autowired
    private AuthenticationSuccessHandler easyAuthenticationSuccessHandler;

    /**
     * 对社交登录的后处理器做加工
     * @see com.bcdbook.security.social.SocialAuthenticationFilterPostProcessor#process(
     * org.springframework.social.security.SocialAuthenticationFilter)
     *
     * @author summer
     * @date 2019-02-20 20:34
     * @param socialAuthenticationFilter 社交登录的过滤器
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        // 为社交登录过滤器, 添加登录成功的处理器
        socialAuthenticationFilter.setAuthenticationSuccessHandler(easyAuthenticationSuccessHandler);
    }

}