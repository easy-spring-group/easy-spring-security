package io.easyspring.security.sso.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 认证服务器的配置
 *
 * @author summer
 * @date 2019-02-23 11:10
 * @version V1.0.0-RELEASE
 */
@Configuration
@EnableAuthorizationServer
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 客户端的配置
     *
     * @author summer
     * @date 2019-02-23 11:11
     * @param clients 客户对象集合
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("spring1")
                .secret("spring-security1")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .redirectUris("http://127.0.0.1:8081/client1/login")
                .scopes("all")
                    .and()
                .withClient("spring2")
                .secret("spring-security2")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .redirectUris("http://127.0.0.1:8082/client2/login")
                .scopes("all");
    }

    /**
     * 配置 token 的存储器
     *
     * @author summer
     * @date 2019-02-23 11:11
     * @param endpoints 验证起点对象
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(jwtTokenStore())
                .accessTokenConverter(jwtAccessTokenConverter());
    }

    /**
     * 获取 jwt token 秘签的配置
     *
     * @author summer
     * @date 2019-02-23 11:12
     * @param security 权限校验的 Security 配置对象
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 这里需要设置其密码校验器为空
//        security.passwordEncoder(NoOpPasswordEncoder.getInstance());
        security.passwordEncoder(new BCryptPasswordEncoder());
        // 获取签名的 key 时需要是已认证状态
        security.tokenKeyAccess("isAuthenticated()");
    }

    /**
     * jwt 存储器的配置
     *
     * @author summer
     * @date 2019-02-23 11:13
     * @return org.springframework.security.oauth2.provider.token.TokenStore
     * @version V1.0.0-RELEASE
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * token 的转换器
     *
     * @author summer
     * @date 2019-02-23 11:13
     * @return org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
     * @version V1.0.0-RELEASE
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        // 创建转换器
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 设置秘签签名
        converter.setSigningKey("spring");
        // 返回转换器
        return converter;
    }

}

