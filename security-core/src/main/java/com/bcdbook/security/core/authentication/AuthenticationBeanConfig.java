/**
 * 
 */
package com.bcdbook.security.core.authentication;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 * 认证相关的扩展点配置。配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置。
 * 
 * @author zhailiang
 *
 */
@Configuration
public class AuthenticationBeanConfig {


    /**
     * 配置 security 的加密器
     *
     * @author summer
     * @date 2019-01-21 16:20
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @version V1.0.0-RELEASE
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        // 创建 security 推荐的加密器
        return new BCryptPasswordEncoder();
    }

    /**
     * 默认的用户详情的 service
     *
     * @author summer
     * @date 2019-01-22 15:18
     * @param
     * @return org.springframework.security.core.userdetails.UserDetailsService
     * @version V1.0.0-RELEASE
     */
	@Bean
	@ConditionalOnMissingBean(UserDetailsService.class)
	public UserDetailsService userDetailsService() {
		return new DefaultUserDetailsService();
	}

//	/**
//	 * 默认认证器
//	 *
//	 * @return
//	 */
//	@Bean
//	@ConditionalOnMissingBean(SocialUserDetailsService.class)
//	public SocialUserDetailsService socialUserDetailsService() {
//		return new DefaultSocialUserDetailsService();
//	}
	
}
