package com.bcdbook.security.core.validate.code;

import com.bcdbook.security.core.properties.SecurityProperties;
import com.bcdbook.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.bcdbook.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
     * 注入 security 的配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 图片验证码图片生成器
     *
     * @author summer
     * @date 2019-01-17 15:35
     * @annotation @ConditionalOnMissingBean(name = "imageCodeGenerator")
     * 当容器中有名字为 imageCodeGenerator 的实现时, 使用新加入的,
     * 当容器中没有新加入的 imageCodeGenerator 实现时, 使用当前的
     *
     * @return com.bcdbook.security.core.validate.code.ValidateCodeGenerator
     * @version V1.0.0-RELEASE
     */
	@Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
	    // 创建图片的生成器
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        // 设置安全配置
        codeGenerator.setSecurityProperties(securityProperties);

        // 返回图片生成器
        return codeGenerator;
    }

    /**
     * 短信验证码的发送器
     * 当使用者有 SmsCodeSender 的实现的时候用调用者的实现,
     * 如果没有, 则使用默认的 {@link DefaultSmsCodeSender}
     *
     * @author summer
     * @date 2019-01-22 11:29
     * @return com.bcdbook.security.core.validate.code.sms.SmsCodeSender
     * @version V1.0.0-RELEASE
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }


}
