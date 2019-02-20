package com.bcdbook.security.app;

import com.bcdbook.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.bcdbook.security.core.properties.SecurityConstants;
import com.bcdbook.security.core.properties.SecurityProperties;
import com.bcdbook.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 开启资源服务器的配置
 *
 * @author summer
 * @date 2019-02-19 18:00
 * @annotation @EnableResourceServer 资源服务器的配置项
 * 当开启了此项配置后, SpringSecurity 就会自动实现资源服务器过滤器链的配置
 *
 * @version V1.0.0-RELEASE
 */
@Configuration
@EnableResourceServer
public class EasyResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler easyAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler easyAuthenticationFailureHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer easySpringSocialConfigurer;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                // 登录页
                .loginPage(SecurityConstants.SignIn.DEFAULT_AUTHENTICATION_URL)
                // 用户登录的接口, SpringSecurity 会监听这个接口, 当有 post 请求时, Security 会执行登录逻辑(不需要我们自己实现)
                .loginProcessingUrl(SecurityConstants.SignIn.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                // 设置登录成功的处理拦截器
                .successHandler(easyAuthenticationSuccessHandler)
                // 等失败的拦截器
                .failureHandler(easyAuthenticationFailureHandler);

        http.apply(validateCodeSecurityConfig)
                	.and()
                .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                .apply(easySpringSocialConfigurer)
                    .and()
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.SignIn.DEFAULT_AUTHENTICATION_URL,
                        SecurityConstants.SignIn.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.Validate.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                        securityProperties.getBrowser().getLoginPage(),
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                        securityProperties.getBrowser().getSignOutUrl(),
                        "/user/regist")
                .permitAll()
                .anyRequest()
                .authenticated()
                    .and()
                .csrf().disable();
    }

}
