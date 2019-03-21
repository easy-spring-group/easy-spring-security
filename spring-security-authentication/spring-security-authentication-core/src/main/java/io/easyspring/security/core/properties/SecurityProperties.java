package io.easyspring.security.core.properties;

import io.easyspring.security.core.properties.browser.BrowserProperties;
import io.easyspring.security.core.properties.code.ValidateCodeProperties;
import io.easyspring.security.core.properties.oauth.OAuth2Properties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Security 的配置类
 *
 * @author summer
 * @version V1.0.0-RELEASE
 * DateTime 2019-01-21 16:36
 */
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "easy-spring.security")
public class SecurityProperties {

    /**
     * 浏览器环境配置
     */
    private BrowserProperties browser = new BrowserProperties();
    /**
     * 验证码配置
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();
    /**
     * OAuth2 的配置
     */
    private OAuth2Properties oauth2 = new OAuth2Properties();

}

