package com.bcdbook.security.core.properties.code;

import com.bcdbook.security.core.properties.SecurityConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证码配置
 *
 * @author summer
 * @date 2019-01-16 20:53
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
public class ValidateCodeProperties {

	/**
	 * 图片验证码配置
	 */
	private ImageCodeProperties image = new ImageCodeProperties();
    /**
     * 短信验证码的配置
     */
    private SmsCodeProperties sms = new SmsCodeProperties();
    /**
     * 验证码的存储器(默认为 session)
     */
    private String repository = SecurityConstants.Validate.DEFAULT_REPOSITORY;
    /**
     * 验证码在 Redis 中存储的有效时长
     */
    private long expire = SecurityConstants.Validate.DEFAULT_EXPIRE;;
}
