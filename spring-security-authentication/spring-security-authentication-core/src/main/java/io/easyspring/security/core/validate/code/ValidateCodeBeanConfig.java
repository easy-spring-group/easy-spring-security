package io.easyspring.security.core.validate.code;

import io.easyspring.security.core.properties.SecurityProperties;
import io.easyspring.security.core.validate.code.sms.DefaultSmsCodeSender;
import io.easyspring.security.core.validate.code.sms.SmsCodeSender;
import io.easyspring.security.core.validate.code.image.ImageCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码相关的扩展点配置。配置在这里的bean，业务系统都可以通过声明同类型或同名的 bean 来覆盖安全
 * 模块默认的配置。
 *
 * @author summer
 * DateTime 2019-01-17 15:33
 * @version V1.0.0-RELEASE
 */
@Configuration
public class ValidateCodeBeanConfig {

    /**
     * 注入 security 的配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 图片验证码图片生成器
     *
     * Author summer
     * DateTime 2019-01-17 15:35
     * Annotation @ConditionalOnMissingBean(name = "imageCodeGenerator")
     * 当容器中有名字为 imageCodeGenerator 的实现时, 使用新加入的,
     * 当容器中没有新加入的 imageCodeGenerator 实现时, 使用当前的
     *
     * @return io.easyspring.security.code.ValidateCodeGenerator
     * Version V1.0.0-RELEASE
     */
	@Bean
	@ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
	public ValidateCodeGenerator imageValidateCodeGenerator() {
		ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
		codeGenerator.setSecurityProperties(securityProperties);
		return codeGenerator;
	}
	
    /**
     * 短信发送器的 Bean
     *
     * @return io.easyspring.security.core.validate.code.sms.SmsCodeSender
     * Author summer
     * Version V1.0.0-RELEASE
     * DateTime 2019-03-21 13:17
     */
	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeSender() {
		return new DefaultSmsCodeSender();
	}


}
