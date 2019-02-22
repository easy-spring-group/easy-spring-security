package com.bcdbook.security.server;

import com.bcdbook.security.app.jwt.EasyJwtTokenEnhancer;
import com.bcdbook.security.app.store.EasyRedisTokenStore;
import com.bcdbook.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Token 存储器的配置类
 *
 * @author summer
 * @date 2019-02-22 11:23
 * @version V1.0.0-RELEASE
 */
@Configuration
public class TokenStoreConfig {

    /**
     * 注入 Redis 的连接工厂
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 配置 token 存储器的 bean
     * 当配置文件中配置了 easy-spring.security.oauth2.token-store=redis 的时候, 会启用当前配置
     * 否则不启用
     *
     * @author summer
     * @date 2019-02-22 11:24
     * @return org.springframework.security.oauth2.provider.token.TokenStore
     * @version V1.0.0-RELEASE
     */
    @Bean
    @ConditionalOnProperty(prefix = "easy-spring.security.oauth2", name = "token-store", havingValue = "redis")
    public TokenStore redisTokenStore() {
        // 创建信息的 RedisToken 存储器
        return new EasyRedisTokenStore(redisConnectionFactory);
    }

    /**
     * JWT 的相关配置
     * 当配置文件中配置了 easy-spring.security.oauth2.token-store=jwt 的时候, 会启用当前配置
     * 同时, 如果没有找到 easy-spring.security.oauth2.token-store 配置项时也是用 jwt
     *
     * @author summer
     * @date 2019-02-22 16:21
     * @version V1.0.0-RELEASE
     */
    @Configuration
    @ConditionalOnProperty(prefix = "easy-spring.security.oauth2", name = "token-store", havingValue = "jwt", matchIfMissing = true)
    public static class JwtConfig {

        /**
         * 注入 security 的配置
         */
        @Autowired
        private SecurityProperties securityProperties;

        /**
         * jwt 存储器的 bean
         *
         * @author summer
         * @date 2019-02-22 16:24
         * @return org.springframework.security.oauth2.provider.token.TokenStore
         * @version V1.0.0-RELEASE
         */
        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * jwt 的 accessToken 转换器
         *
         * @author summer
         * @date 2019-02-22 16:25
         * @return org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
         * @version V1.0.0-RELEASE
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            // 创建转换器
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            // 设置秘签的 key
            converter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            // 返回生成的转换器
            return converter;
        }

        /**
         * jwt 的增强器
         * 如果用户没有配置增强器, 则使用此增强器
         *
         * @author summer
         * @date 2019-02-22 16:38
         * @return org.springframework.security.oauth2.provider.token.TokenEnhancer
         * @version V1.0.0-RELEASE
         */
        @Bean
        @ConditionalOnMissingBean(TokenEnhancer.class)
        public TokenEnhancer jwtTokenEnhancer(){
            return new EasyJwtTokenEnhancer();
        }
    }


}
