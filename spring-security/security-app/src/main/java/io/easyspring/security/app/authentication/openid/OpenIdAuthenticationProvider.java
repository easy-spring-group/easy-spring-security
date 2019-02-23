package io.easyspring.security.app.authentication.openid;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 用于实际校验的校验器
 * 根据 Filter 中配置的 manager 中设置 token 类型去找一个校验器
 *
 * @author summer
 * @date 2019-02-21 11:06
 * @version V1.0.0-RELEASE
 */
@Data
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    /**
     * 社交登录用户详情的 service
     */
    private SocialUserDetailsService userDetailsService;
    /**
     * 社交登录的连接信息存储器
     */
    private UsersConnectionRepository usersConnectionRepository;

    /**
     * 重写用户认证的方法, 此方法由 {@link OpenIdAuthenticationFilter#attemptAuthentication(
     * javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)}
     * 中的 this.getAuthenticationManager().authenticate(authRequest); 来调用的,
     * 实际使用中 AuthenticationManager 会根据 token 的类型自己去匹配
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(
     * org.springframework.security.core.Authentication)
     *
     * @author summer
     * @date 2019-02-21 11:09
     * @param authentication 认证信息
     * @return org.springframework.security.core.Authentication
     * @version V1.0.0-RELEASE
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 转换认证信息为 OpenIdAuthenticationToken
        OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) authentication;

        // 创建集合, 用于存储用户的 openId
        Set<String> providerUserIds = new HashSet<>();
        // 添加用户的 openId
        providerUserIds.add((String) authenticationToken.getPrincipal());

        /*
         * 获取社交登录的认证信息
         */
        // 根据服务提供商的 id 和 openId, 从社交登录信息的表中获取对应的认证数据
        Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(
                authenticationToken.getProviderId(), providerUserIds);

        // 对获取到的认证数据进行校验
        if(CollectionUtils.isEmpty(userIds) || userIds.size() != 1) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        // 获取用户的认证数据
        String userId = userIds.iterator().next();

        /*
         * 获取用户信息 (自己维护的表数据)
         */
        // 通过认证数据, 获取用户详情信息
        UserDetails user = userDetailsService.loadUserByUserId(userId);
        // 如果不存在相应的用户, 则直接抛出异常
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        /*
         * 封装认证信息
         */
        // 创建 openId 的认证信息
        OpenIdAuthenticationToken authenticationResult = new OpenIdAuthenticationToken(user, user.getAuthorities());
        // 设置认证信息详情
        authenticationResult.setDetails(authenticationToken.getDetails());

        // 返回认证好的信息
        return authenticationResult;
    }

    /**
     * Provider 的匹配器,
     * AuthenticationManager 就是通过这个匹配器去查找对应的 provider 进行具体的校验
     * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
     *
     * @author summer
     * @date 2019-02-21 11:26
     * @param authentication 权限校验的类
     * @return boolean
     * @version V1.0.0-RELEASE
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
