package io.easyspring.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 短信登录配置
 * 
 * @author zhailiang
 *
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends
        SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    /**
     * 自定义的登录成功处理器
     */
	@Autowired
	private AuthenticationSuccessHandler easyAuthenticationSuccessHandler;
    /**
     * 自定义的登录失败处理器
     */
	@Autowired
	private AuthenticationFailureHandler easyAuthenticationFailureHandler;
    /**
     * 用户详情的 service 实现
     * 因为此处的实现是在调用方进行实现的, 所以会报无实现的风险
     */
	@Autowired
	private UserDetailsService userDetailsService;

    /**
     * 配置短信验证码的登录拦截
     *
     * @author summer
     * @date 2019-01-22 15:01
     * @param http 请求信
     * @return void
     * @version V1.0.0-RELEASE
     */
	@Override
	public void configure(HttpSecurity http) throws Exception {
	    // 创建手机验证码等的拦截器
		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
		// 设置拦截器的控制器
		smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		// 设置成功处理器
		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(easyAuthenticationSuccessHandler);
		// 设置失败处理器
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(easyAuthenticationFailureHandler);

        // 创建短信验证码的处理器
		SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
		// 为处理器设置用户 service
		smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

		// 把短信验证码的校验器设置到用户名密码的校验器之后
		http.authenticationProvider(smsCodeAuthenticationProvider)
			.addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	}

}
