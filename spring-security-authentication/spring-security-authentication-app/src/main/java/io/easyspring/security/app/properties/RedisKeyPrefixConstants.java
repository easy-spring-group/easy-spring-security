package io.easyspring.security.app.properties;

/**
 * Redis 的 key 前缀的常量类
 *
 * @author summer
 * DateTime 2019-02-21 15:41
 * @version V1.0.0-RELEASE
 */
public interface RedisKeyPrefixConstants {

    /**
     * 验证码在 Redis 中存储的结构
     * 结构解释: easy:spring:validate:code:{TYPE}:{DEVICEId}
     */
    String VALIDATE_CODE_TEMPLATE = "easy:spring:validate:code:%s:%s";

    /**
     * 社交登录认证信息在 Redis 中存储的结构
     * 结构解释: easy:spring:security:social:connect:{DEVICEId}
     */
    String SOCIAL_CONNECT_DATA_TEMPLATE = "easy:spring:security:social:connect:%s";

}
