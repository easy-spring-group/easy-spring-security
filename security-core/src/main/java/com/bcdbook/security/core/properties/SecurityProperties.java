package com.bcdbook.security.core.properties;

import com.bcdbook.security.core.properties.browser.BrowserProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Security 的配置类
 *
 * @author summer
 * @date 2019-01-21 16:36
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "easy-spring.security")
public class SecurityProperties {

    /**
     * 浏览器环境配置
     */
    private BrowserProperties browser = new BrowserProperties();
}

