package io.easyspring.security.server;

import io.easyspring.security.core.properties.SecurityProperties;
import io.easyspring.security.core.properties.oauth.OAuth2ClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
@Slf4j
public class EasyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 权限校验的 管理器
     */
    private final AuthenticationManager authenticationManager;
    /**
     * 构造方法, 用于设置 authenticationManager
     *
     * @author summer
     * @date 2019-02-22 12:17
     * @param authenticationConfiguration 权限认证配置器
     * @version V1.0.0-RELEASE
     */
    public EasyAuthorizationServerConfig(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 注入用户的 service
     */
    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * 注入 token 的存储器
     */
    @Autowired
    private TokenStore tokenStore;
    /**
     * 注入 jwt 的转换器
     */
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    /**
     * 注入 jwt 的增强器
     */
    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    /**
     * 注入 security 的配置
     */
    @Autowired
    private SecurityProperties securityProperties;


    /**
     * 设置 OAuth2 的入口信息
     *
     * @author summer
     * @date 2019-02-22 11:27
     * @param endpoints 权限校验入口点
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 设置 token 的存储器
        endpoints.tokenStore(tokenStore)
                // 设置权限校验器
                .authenticationManager(this.authenticationManager)
                // 设置用户的 service
                .userDetailsService(userDetailsService);

        /*
         * 设置 jwt 的相关信息
         */
        // 如果 jwt 的连接器和增强器均不为空
        if(jwtAccessTokenConverter != null && jwtTokenEnhancer != null){
            // 创建 token 的转换器集合
            List<TokenEnhancer> enhancers = new ArrayList<>();
            // 添加增强器
            enhancers.add(jwtTokenEnhancer);
            // 添加转换器
            enhancers.add(jwtAccessTokenConverter);

            // 创建 token 的增强器链
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            enhancerChain.setTokenEnhancers(enhancers);
            // 把我们的 token 增强器添加到配置中
            endpoints.tokenEnhancer(enhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }

    }


    /**
     * 配置支持的认证服务信息
     *
     * @author summer
     * @date 2019-02-20 18:17
     * @param clients clientDetails 配置器
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 获取客户认证的内存处理器
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        // 如果配置了 client 信息
        if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {

            // 获取 OAuth2 的 client 配置
            OAuth2ClientProperties[] clientsInCustom = securityProperties.getOauth2().getClients();
            // 循环设置 client 信息
            for (OAuth2ClientProperties client : clientsInCustom) {

                /*
                 * 设置 client 信息
                 */
                // 设置 client id
                builder.withClient(client.getClientId())
                        // 设置密码
                        .secret(client.getClientSecret())
                        // 设置支持的认证类型
                        .authorizedGrantTypes(client.getAuthorizedGrantTypes())
                        // 设置 accessToken 的有效期
                        .accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())
                        // 设置 refreshToken 的有效期
                        .refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds())
                        // 设置允许的授权范围
                        .scopes(client.getScopes());
            }

            log.info("OAuth2 的授权信息: {}", Arrays.toString(clientsInCustom));
        }
    }


    /**
     * 设置 client 认证时的加密器
     *
     * @author summer
     * @date 2019-02-22 14:31
     * @param security 加密的配置项
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 这里使用什么密码需要 根据上面配置 client 信息里面的密码类型决定
        // 目前上面配置的是无加密的密码
        security.passwordEncoder(new BCryptPasswordEncoder());
    }
}
