package com.bcdbook.security.core.authentication.mobile;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 短信验证码验证的提供者
 *
 * @author summer
 * @date 2019-01-22 13:04
 * @version V1.0.0-RELEASE
 */
public class SmsCodeAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
     * Provider 调用的匹配器
     *
     * @author summer
     * @date 2019-01-22 13:53
     * @param authentication 需要校验的验证类型
     * @return boolean
     * @version V1.0.0-RELEASE
     */
    @Override
    public boolean supports(Class<?> authentication) {
        /*
         * 根据不同的 AuthenticationToken spring 会调用不同的 Provider 来处理
         * 此处就是判断传入的 authentication 是否是 SmsCodeAuthenticationToken 类型的
         * 如果是, Spring 则会调用 SmsCodeAuthenticationProvider 来处理
         */
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        //do noting
    }

    /**
     * 获取用户信息的方法
     *
     * @author summer
     * @date 2019-01-22 13:57
     * @param username 用户名(此处为手机号)
     * @param authentication 登录信息存储器
     * @return org.springframework.security.core.userdetails.UserDetails
     * @version V1.0.0-RELEASE
     */
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        // 查找用户信息
        UserDetails user = userDetailsService.loadUserByUsername(username);
        // 如果用户信息为空, 则抛出异常
        if(user == null) {
            throw new InternalAuthenticationServiceException("未获取到用户信息");
        }

        // 返回查找到的用户新
        return user;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }



}
