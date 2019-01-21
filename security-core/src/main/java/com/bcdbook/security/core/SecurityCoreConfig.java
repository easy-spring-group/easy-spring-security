package com.bcdbook.security.core;

import com.bcdbook.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * security 核心的配置文件
 *
 * @annotaion @Configuration 表明这是一个配置类
 * @annotaion @EnableConfigurationProperties 此配置文件激活的配置类
 *
 * @author summer
 * @date 2019-01-21 16:34
 * @version V1.0.0-RELEASE
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
