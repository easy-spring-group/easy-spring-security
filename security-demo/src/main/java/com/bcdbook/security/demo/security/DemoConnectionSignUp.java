package com.bcdbook.security.demo.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * 通过第三方自动注册的实现
 *
 * @author summer
 * @date 2019-01-25 15:47
 * @version V1.0.0-RELEASE
 */
//@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

    /**
     * 没有对应的用户时自动创建用户的方法
     *
     * @author summer
     * @date 2019-01-25 15:48
     * @param connection social 返回的信息
     * @return java.lang.String
     * @version V1.0.0-RELEASE
     */
    @Override
    public String execute(Connection<?> connection) {
        // 根据社交用户信息默认创建用户并返回用户唯一标识
        // 真实逻辑需要根据自己的需求修改
        return connection.getKey().getProviderUserId();
    }

}
