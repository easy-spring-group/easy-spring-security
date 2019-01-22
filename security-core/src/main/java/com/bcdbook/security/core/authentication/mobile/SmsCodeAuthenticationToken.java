package com.bcdbook.security.core.authentication.mobile;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * 用于封装验证信息的对象
 * 登录成功之前存储的是登录的手机号, 登录成功之后存储的是认证成功后的信息
 *
 * @author summer
 * @date 2019-01-22 13:02
 * @version V1.0.0-RELEASE
 */
public class SmsCodeAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 7772703991322484628L;

    /**
     * 构造方法, 用于存储验证信息
     *
     * @author summer
     * @date 2019-01-22 13:02
     * @param principal 验证信息
     * @version V1.0.0-RELEASE
     */
    public SmsCodeAuthenticationToken(Object principal) {
        super(principal, null);
    }

}
