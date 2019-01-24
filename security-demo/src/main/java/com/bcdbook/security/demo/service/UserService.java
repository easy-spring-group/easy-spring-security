package com.bcdbook.security.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

/**
 * 用户的 service
 *
 * @author summer
 * @date 2019-01-21 16:17
 * @version V1.0.0-RELEASE
 */
@Service
@Slf4j
public class UserService implements UserDetailsService, SocialUserDetailsService {

    /**
     * 加密解密器
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用名获取用户详情的方法
     * 当我们自己实现 UserDetailsService 并注入到 spring 后, security 在登录的时候就会调用此方法获取用户
     * 此处可以扩展自己的用户校验逻辑
     *
     * @author summer
     * @date 2019-01-21 16:17
     * @param username 用户名
     * @return org.springframework.security.core.userdetails.UserDetails
     * @version V1.0.0-RELEASE
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录用户名:" + username);

        return buildUser(username);
    }


    /**
     * 根据 social 的 id 获取用户详情的方法
     *
     * @author summer
     * @date 2019-01-24 14:44
     * @param socialUserId social 的 id
     * @return org.springframework.social.security.SocialUserDetails
     * @version V1.0.0-RELEASE
     */
    @Override
    public SocialUserDetails loadUserByUserId(String socialUserId) throws UsernameNotFoundException {
        log.info("第三方登录的 id: {}", socialUserId);
        // 封装用户详情, 并返回
        return buildUser(socialUserId);

    }

    /**
     * 根据 social 的用户 id 获取用户详情的方法
     *
     * @author summer
     * @date 2019-01-24 14:48
     * @param userId social 中存储的用户 id(可以是用户名等唯一标识)
     * @return org.springframework.social.security.SocialUserDetails
     * @version V1.0.0-RELEASE
     */
    private SocialUserDetails buildUser(String userId) {
        // 根据用户名查找用户信息
        // 根据查找到的用户信息判断用户是否被冻结
        String password = passwordEncoder.encode("123123");
        log.info("数据库密码是:"+password);

        /*
         * 这里返回的 user 是 Security 的 User 对象,
         * 也可以用自己的 user 实现 UserDetails 接口来实例化这个 User 对象
         */
        return new SocialUser(
                // 用户名
                userId,
                // 密码
                password,
                // 是否是启用状态
                true,
                // 账户是否是未过期的
                true,
                // 密码是否是未过期的
                true,
                // 是否是未锁定
                true,
                // 把权限字符串转换成权限集合, 设置到 user 中
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }


}
