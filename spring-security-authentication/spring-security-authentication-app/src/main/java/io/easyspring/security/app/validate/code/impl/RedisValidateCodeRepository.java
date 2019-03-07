package io.easyspring.security.app.validate.code.impl;

import io.easyspring.security.app.properties.RedisKeyPrefixConstants;
import io.easyspring.security.core.properties.SecurityConstants;
import io.easyspring.security.core.properties.SecurityProperties;
import io.easyspring.security.core.validate.code.ValidateCode;
import io.easyspring.security.core.validate.code.ValidateCodeException;
import io.easyspring.security.core.validate.code.ValidateCodeRepository;
import io.easyspring.security.core.validate.code.ValidateCodeType;
import io.easyspring.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * Redis 的存储器, 用于存储用户的验证码
 * 当配置了存储器为 Redis 的时候生效
 *
 * @author summer
 * @date 2019-01-23 14:54
 * @version V1.0.0-RELEASE
 */
@Component
@ConditionalOnProperty(prefix = "easy-spring.security.code", name = "repository", havingValue = "REDIS")
@Slf4j
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    /**
     * 注入 Redis 的模板
     */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    /**
     * 注入安全配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 保存验证码的方法
     *
     * @author summer
     * @date 2019-01-23 15:01
     * @param request 请求及返回信息
     * @param code 验证码
     * @param validateCodeType 验证码类型
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        // 获取存在 Redis 中的验证码的 key
        String key = buildKey(request, validateCodeType);
        // 存储到 Redis 数据库中
        redisTemplate.opsForValue()
                .set(key, code, securityProperties.getCode().getExpire(), TimeUnit.SECONDS);
    }

    /**
     * 获取验证码的方法
     *
     * @author summer
     * @date 2019-01-23 15:12
     * @param request 请求信息
     * @param validateCodeType 验证码类型
     * @return io.easyspring.security.core.validate.code.ValidateCode
     * @version V1.0.0-RELEASE
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        // 获取存在 Redis 中的验证码的 key
        String key = buildKey(request, validateCodeType);
        // 拿到创建 create() 存储到 Redis 的 code 验证码对象, 并返回
        return (ValidateCode) redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除验证码的方法
     *
     * @author summer
     * @date 2019-01-23 15:13
     * @param request 请求信息
     * @param validateCodeType 验证码类型
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        // 获取存在 Redis 中的验证码的 key
        String key = buildKey(request, validateCodeType);
        // 执行删除操作
        redisTemplate.delete(key);
    }

    /**
     * 构建验证码放入 redis 时的 key; 在保存的时候也使用该 key
     * {@link AbstractValidateCodeProcessor}
     *
     * @author summer
     * @date 2019-01-23 15:14
     * @param request 请求信息
     * @param validateCodeType 验证码类型
     * @return java.lang.String
     * @version V1.0.0-RELEASE
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType validateCodeType) {

        // 从请求中获取 deviceId
        String deviceId = (String) request.getAttribute(SecurityConstants.Validate.DEFAULT_HEADER_DEVICE_ID_KEY,
                SecurityConstants.Validate.DEFAULT_DEVICE_ID_EXPIRE);

        log.info("验证时获取到的 deviceId 是: {}", deviceId);

        // 对 deviceId 进行校验
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("获取 deviceId 失败");
        }

        // 返回格式化好的 Redis 的 key
        return String.format(RedisKeyPrefixConstants.VALIDATE_CODE_TEMPLATE, validateCodeType, deviceId);
    }
}
