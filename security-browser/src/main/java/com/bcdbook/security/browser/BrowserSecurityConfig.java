package com.bcdbook.security.browser;

import com.bcdbook.security.core.properties.SecurityProperties;
import com.bcdbook.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * 浏览器安全的配置类
 *
 * @author summer
 * @date 2019-01-21 14:40
 * @version V1.0.0-RELEASE
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 注入 security 的配置文件
     */
    @Resource
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler browserAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler browserAuthenticationFailureHandler;

    /**
     * 重写父级的 security 配置, 使用自己的安全验证方案
     *
     * @author summer
     * @date 2019-01-21 14:41
     * @param http 安全请求对象
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 在 v5+ 中，该配置（表单登录）是默认配置
        // basic 登录（也就是弹框登录的）v5- 的版本默认

        // 创建验证码的过滤器
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        // 设置验证码验证失败的处理器
        validateCodeFilter.setAuthenticationFailureHandler(browserAuthenticationFailureHandler);


        /*
         * 使用表单的方式登录
         * 最简单的修改默认配置的方法
         * 在 v5+ 中，该配置（表单登录）是默认配置
         */
		http
                // 在 UsernamePasswordAuthenticationFilter 过滤器之前加上验证码的过滤器
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                // 登录页
                .loginPage("/authentication/require")
                // 用户登录的接口, SpringSecurity 会监听这个接口, 当有 post 请求时, Security 会执行登录逻辑(不需要我们自己实现)
                .loginProcessingUrl("/authentication/form")

                // 设置登录成功的处理拦截器
                .successHandler(browserAuthenticationSuccessHandler)
                // 等失败的拦截器
                .failureHandler(browserAuthenticationFailureHandler)


                .and()
                // 权限校验规则
                .authorizeRequests()

                // 请求拦截时, 忽略一下路径
                .antMatchers("/authentication/require",
                        "/code/image",
                        securityProperties.getBrowser().getLoginPage())
                    .permitAll()

                // 所有的权限校验
                .anyRequest()
                // 都需要权限校验
                .authenticated()

                .and()
                /*
                 * 忽略对伪造身份的拦截,
                 * v5+ 如果不忽略这个, 不会报错, 同时自定义的登录页面登录总是无效
                 * v5- 的时候, 如果不添加这个配置, 登录时会报错
                 */
                .csrf().disable();

    }

    /**
     * 配置 security 的加密器
     *
     * @author summer
     * @date 2019-01-21 16:20
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @version V1.0.0-RELEASE
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 创建 security 推荐的加密器
        return new BCryptPasswordEncoder();
    }

}
