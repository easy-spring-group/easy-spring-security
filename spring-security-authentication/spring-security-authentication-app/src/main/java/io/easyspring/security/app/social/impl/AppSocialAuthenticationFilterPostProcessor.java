package io.easyspring.security.app.social.impl;

import io.easyspring.security.social.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 社交登录的后处理器
 * TODO 此实现之后需要提取到 具体的使用者中
 *
 * @author summer
 * DateTime 2019-02-20 20:33
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
     * @see SocialAuthenticationFilterPostProcessor#process(
     * org.springframework.social.security.SocialAuthenticationFilter)
     *
     * Author summer
     * DateTime 2019-02-20 20:34
     * @param socialAuthenticationFilter 社交登录的过滤器
     * Version V1.0.0-RELEASE
     */
    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        // 为社交登录过滤器, 添加登录成功的处理器
        socialAuthenticationFilter.setAuthenticationSuccessHandler(easyAuthenticationSuccessHandler);
    }

}
