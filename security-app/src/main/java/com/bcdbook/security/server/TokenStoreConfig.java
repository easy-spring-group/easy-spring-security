package com.bcdbook.security.server;

import com.bcdbook.security.app.store.EasyRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

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
     *
     * @author summer
     * @date 2019-02-22 11:24
     * @return org.springframework.security.oauth2.provider.token.TokenStore
     * @version V1.0.0-RELEASE
     */
    @Bean
    public TokenStore redisTokenStore() {
        // 创建信息的 RedisToken 存储器
        return new EasyRedisTokenStore(redisConnectionFactory);
    }

}
