package io.easyspring.security.core.properties.code;

import lombok.Data;

/**
 * 短信验证码的配置
 *
 * @author summer
 * @date 2019-01-22 11:08
 * @version V1.0.0-RELEASE
 */
@Data
public class SmsCodeProperties {
    /**
     * 验证码长度
     */
    private int length = 4;
    /**
     * 有效时长 (单位是: 秒)
     */
    private int expire = 60;

}
