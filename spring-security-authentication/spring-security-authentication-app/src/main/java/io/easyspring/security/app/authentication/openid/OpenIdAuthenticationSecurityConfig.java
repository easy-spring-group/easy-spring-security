package io.easyspring.security.app.authentication.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 社交登录校验的配置类
 *
 * @author summer
 * DateTime 2019-02-21 11:29
 * @version V1.0.0-RELEASE
 */
@Component
public class OpenIdAuthenticationSecurityConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    /**
     * 注入校验成功处理器
     */
    @Autowired
    private AuthenticationSuccessHandler easyAuthenticationSuccessHandler;
    /**
     * 注入校验失败的处理器
     */
    @Autowired
    private AuthenticationFailureHandler easyAuthenticationFailureHandler;
    /**
     * 注入用户详情 service
     */
    @Autowired
    private SocialUserDetailsService userDetailsService;
    /**
     * 注入社交登录认证的存储器
     */
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    /**
     * 重写配置类
     *
     * Author summer
     * DateTime 2019-02-21 11:31
     * @param http security 的请求对象
     * Version V1.0.0-RELEASE
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        /*
         * 设置社交登录认证的过滤器
         */
        // 创建社交登录认证的过滤器
        OpenIdAuthenticationFilter openIdAuthenticationFilter = new OpenIdAuthenticationFilter();
        // 设置认证管理器
        openIdAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        // 设置登录成功处理器
        openIdAuthenticationFilter.setAuthenticationSuccessHandler(easyAuthenticationSuccessHandler);
        // 设置登录失败处理器
        openIdAuthenticationFilter.setAuthenticationFailureHandler(easyAuthenticationFailureHandler);

        /*
         * 设置社交登录认证的认证器
         */
        // 创建认证器
        OpenIdAuthenticationProvider openIdAuthenticationProvider = new OpenIdAuthenticationProvider();
        // 设置用户详情 service
        openIdAuthenticationProvider.setUserDetailsService(userDetailsService);
        // 设置社交登录的存储器
        openIdAuthenticationProvider.setUsersConnectionRepository(usersConnectionRepository);

        // 设置认证器
        http.authenticationProvider(openIdAuthenticationProvider)
                // 设置后过滤器
                .addFilterAfter(openIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
