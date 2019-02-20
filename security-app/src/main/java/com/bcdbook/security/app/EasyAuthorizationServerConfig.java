package com.bcdbook.security.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 实现认证服务器的配置类
 *
 * @author summer
 * @annotation @EnableAuthorizationServer 是开启认证服务器的注解,
 * 当配置了此项后, SpringSecurity 就会开启 4 种授权模式的实现
 *
 * @date 2019-02-19 17:56
 * @version V1.0.0-RELEASE
 */
@Configuration
@EnableAuthorizationServer
public class EasyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 配置支持的认证服务信息
     * TODO 后期需要优化此配置
     *
     * @author summer
     * @date 2019-02-20 18:17
     * @param clients clientDetails 配置器
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("test")
                .secret("test")
                .accessTokenValiditySeconds(600)
                .and()
                .withClient("easy-spring")
                .secret("easy-spring-secret")
                .accessTokenValiditySeconds(1200);
    }

}
