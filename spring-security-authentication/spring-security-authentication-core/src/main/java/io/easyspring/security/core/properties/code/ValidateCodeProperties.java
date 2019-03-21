package io.easyspring.security.core.properties.code;

import io.easyspring.security.core.properties.SecurityConstants;
import io.easyspring.security.core.properties.code.enums.ValidateCodeRepositoryTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证码配置
 *
 * @author summer
 * @version V1.0.0-RELEASE
 * DateTime 2019-01-16 20:53
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
     * 需要验证码验证的集合
     * 需要拦截的地址及拦截类型
     */
    private List<ValidateCodeFilterUrlProperties> filterUrls = new ArrayList<>();
    /**
     * 验证码的存储器(默认为 session)
     */
    private ValidateCodeRepositoryTypeEnum repository = ValidateCodeRepositoryTypeEnum.SESSION;
    /**
     * 验证码在 Redis 中存储的有效时长
     */
    private long expire = SecurityConstants.Validate.DEFAULT_EXPIRE;
}
