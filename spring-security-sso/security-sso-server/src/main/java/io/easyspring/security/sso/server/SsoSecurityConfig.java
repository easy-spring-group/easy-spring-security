package io.easyspring.security.sso.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SSO Security 的配置类
 *
 * @author summer
 * @date 2019-02-23 09:51
 * @version V1.0.0-RELEASE
 */
@Configuration
public class SsoSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 注入用户的 service
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 密码加密器
     *
     * @author summer
     * @date 2019-02-23 09:52
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @version V1.0.0-RELEASE
     */
    @Bean
    public PasswordEncoder passwordEncoder()	{
        return new BCryptPasswordEncoder();
    }

    /**
     * 权限校验管理器的配置
     *
     * @author summer
     * @date 2019-02-23 09:56
     * @param auth 管理器对象
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * http 的安全配置
     *
     * @author summer
     * @date 2019-02-23 09:53
     * @param http 需要配置的 http 安全对象
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic()
//                .and()
//                .csrf().disable();

        http.formLogin()
                    .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                    .and()
                .csrf().disable();
    }

}
