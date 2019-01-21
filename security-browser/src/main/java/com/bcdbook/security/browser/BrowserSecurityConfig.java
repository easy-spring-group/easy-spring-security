package com.bcdbook.security.browser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        // basic 登录（也就是弹框登录的）v5-的版本默认

        /*
         * 使用表单的方式登录
         * 最简单的修改默认配置的方法
         * 在 v5+ 中，该配置（表单登录）是默认配置
         */
		http.formLogin()
        // 使用 basic 的方式登录（也就是弹框登录的）v5- 的版本默认
        // http.httpBasic()
                .and()
                // 权限校验规则
                .authorizeRequests()
                // 所有的权限校验
                .anyRequest()
                // 都需要权限校验
                .authenticated();

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
