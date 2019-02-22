package com.bcdbook.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;

/**
 * 校验码相关安全配置
 *
 * @author summer
 * @date 2019-01-17 18:00
 * @version V1.0.0-RELEASE
 */
@Component("validateCodeSecurityConfig")
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    /**
     * 注入校验码的过滤器
     */
	@Autowired
	private Filter validateCodeFilter;
	
    /**
     * 重写父级的配置方法, 把自己的过滤器加入到过滤器链中
     *
     * @author summer
     * @date 2019-01-17 18:00
     * @param http Security 的 http 请求
     * @return void
     * @version V1.0.0-RELEASE
     */
	@Override
	public void configure(HttpSecurity http) throws Exception {
        // 在认证与处理的过滤器之前加入验证码校验器
		http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
	}
	
}
