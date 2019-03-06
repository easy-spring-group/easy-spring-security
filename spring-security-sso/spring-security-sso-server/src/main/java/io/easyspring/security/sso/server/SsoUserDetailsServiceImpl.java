package io.easyspring.security.sso.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 实现 userService 用于获取 user 对象, 以供认证
 *
 * @author summer
 * @date 2019-02-23 10:00
 * @version V1.0.0-RELEASE
 */
@Component
public class SsoUserDetailsServiceImpl implements UserDetailsService {

    /**
     * 注入密码加密器
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 重写根据用户名获取用户的方法
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     *
     * @author summer
     * @date 2019-02-23 10:01
     * @param username 用户名
     * @return org.springframework.security.core.userdetails.UserDetails
     * @version V1.0.0-RELEASE
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User(username,
                passwordEncoder.encode("123123"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));

        return user;
    }

}

