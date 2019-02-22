package com.bcdbook.security.server;

import com.bcdbook.security.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.bcdbook.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.bcdbook.security.core.properties.SecurityConstants;
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

    /**
     * 注入认证成功处理器
     */
    @Autowired
    protected AuthenticationSuccessHandler easyAuthenticationSuccessHandler;
    /**
     * 注入认证失败处理器
     */
    @Autowired
    protected AuthenticationFailureHandler easyAuthenticationFailureHandler;
    /**
     * 注入手机验证码, 认证的配置
     */
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    /**
     * 注入 openId 验证的配置
     */
    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;
    /**
     * 注入验证码, 验证的配置
     */
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    /**
     * 注入 SpringSocial 的相关配置
     */
    @Autowired
    private SpringSocialConfigurer easySpringSocialConfigurer;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        /*
         * 表单登录的配置
         */
        http.formLogin()
                // 登录页
                .loginPage(SecurityConstants.SignIn.DEFAULT_AUTHENTICATION_URL)
                // 用户登录的接口, SpringSecurity 会监听这个接口, 当有 post 请求时, Security 会执行登录逻辑(不需要我们自己实现)
                .loginProcessingUrl(SecurityConstants.SignIn.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                // 设置登录成功的处理拦截器
                .successHandler(easyAuthenticationSuccessHandler)
                // 等失败的拦截器
                .failureHandler(easyAuthenticationFailureHandler);

        /*
         * 其他配置的加入
         */
        // 加入验证码验证的配置
        http.apply(validateCodeSecurityConfig)
                	.and()
                // 加入手机短信认证的配置
                .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                // 加入 SpringSocial 认证的配置
                .apply(easySpringSocialConfigurer)
                    .and()
                // 加入 openId 认证的配置
                .apply(openIdAuthenticationSecurityConfig)
                    .and()
                .authorizeRequests()
                .antMatchers(
                        // 忽略登录的地址
                        SecurityConstants.SignIn.DEFAULT_AUTHENTICATION_URL,
                        // 忽略手机验证码登录的地址
                        SecurityConstants.SignIn.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                        // 忽略 openId 登录的地址
                        SecurityConstants.Social.DEFAULT_LOGIN_PROCESSING_URL_OPENID,
                        // 忽略验证码获取及验证的地址
                        SecurityConstants.Validate.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                        // TODO 需要抽取
                        "/user/regist", "/social/signUp", "/oauth/token")
                .permitAll()
                .anyRequest()
                .authenticated()
                    .and()
                .csrf().disable();
    }

}