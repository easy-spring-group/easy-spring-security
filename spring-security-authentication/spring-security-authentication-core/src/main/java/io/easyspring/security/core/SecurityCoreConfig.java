package io.easyspring.security.core;

import io.easyspring.security.core.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * security 核心的配置文件
 *
 * Annotation @Configuration 表明这是一个配置类
 * Annotation @EnableConfigurationProperties 此配置文件激活的配置类
 *
 * @author summer
 * DateTime 2019-01-21 16:34
 * @version V1.0.0-RELEASE
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

    /**
     * 配置 security 的加密器
     *
     * Author summer
     * DateTime 2019-01-21 16:20
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * Version V1.0.0-RELEASE
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        // 创建 security 推荐的加密器
        return new BCryptPasswordEncoder();
    }
}
