package io.easyspring.security.core.authentication.mobile;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 短信验证码验证的处理器
 * 由于短信验证码的验证在过滤器里已完成，这里直接读取用户信息即可。
 *
 * @author summer
 * DateTime 2019-01-22 13:04
 * @version V1.0.0-RELEASE
 */
@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
     * Provider 调用的匹配器
     *
     * Author summer
     * DateTime 2019-01-22 13:53
     * @param authentication 需要校验的验证类型
     * @return boolean
     * Version V1.0.0-RELEASE
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

    /**
     * 获取用户信息的方法
     *
     * Author summer
     * DateTime 2019-01-22 13:57
     * @param authentication 登录信息存储器
     * @return org.springframework.security.core.userdetails.UserDetails
     * Version V1.0.0-RELEASE
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 把获取到的信息转换成手机验证码的存储器
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;

        // 获取用户对象
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        // 如果用户信息为空, 则抛出异常
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        // 使用登录后的用户, 重新构建一个用户信息从存储器, 此次构建需要设置当前用户获取到的权限信息
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
        // 设置授权的详细信息
        authenticationResult.setDetails(authenticationToken.getDetails());

        // 返回授权结果
        return authenticationResult;
    }

}
