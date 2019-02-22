package com.bcdbook.security.core.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 用于封装验证信息的对象
 * 登录成功之前存储的是登录的手机号, 登录成功之后存储的是认证成功后的信息
 *
 * @author summer
 * @date 2019-01-22 13:02
 * @version V1.0.0-RELEASE
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 7772703991322484628L;

    /**
     * 存放用户名 ： credentials 字段去掉，因为短信认证在授权认证前已经过滤了
     */
    private final Object principal;

    /**
     * 构造方法, 用于存储验证信息
     *
     * @author summer
     * @date 2019-01-22 16:08
     * @param mobile 手机号
     * @version V1.0.0-RELEASE
     */
    public SmsCodeAuthenticationToken(String mobile) {
        // 调用父类的构造方法
        super(null);
        // 设置认证信息
        this.principal = mobile;
        // 设置已授权状态为 false
        setAuthenticated(false);
    }

    /**
     * 认证后的构造方法
     *
     * @author summer
     * @date 2019-01-22 16:11
     * @param principal 认证依据
     * @param authorities 授予的权限信息
     * @version V1.0.0-RELEASE
     */
    public SmsCodeAuthenticationToken(Object principal,
                                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }


    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

}
