package com.bcdbook.security.core.validate.code;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码相关的扩展点配置。配置在这里的bean，业务系统都可以通过声明同类型或同名的 bean 来覆盖安全
 * 模块默认的配置。
 *
 * @author summer
 * @date 2019-01-17 15:33
 * @version V1.0.0-RELEASE
 */
@Configuration
public class ValidateCodeBeanConfig {

    /**
     * 图片验证码图片生成器
     *
     * @author summer
     * @date 2019-01-17 15:35
     * @return com.bcdbook.security.core.validate.code.ValidateCodeGenerator
     * @version V1.0.0-RELEASE
     */
	@Bean
	public ValidateCodeGenerator imageCodeGenerator() {
		return new DefaultImageCodeGenerator();
	}

}
