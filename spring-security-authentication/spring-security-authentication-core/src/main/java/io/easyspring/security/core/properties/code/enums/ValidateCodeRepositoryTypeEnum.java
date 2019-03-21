package io.easyspring.security.core.properties.code.enums;

/**
 * 验证码存储器的类型
 *
 * @author summer
 * @version V1.0.0-RELEASE
 * DateTime 2019-02-20 18:37
 */
public enum ValidateCodeRepositoryTypeEnum {
    /**
     * session 形式存储
     */
    SESSION,
    /**
     * Redis 形式存储
     */
    REDIS
    ;
}
