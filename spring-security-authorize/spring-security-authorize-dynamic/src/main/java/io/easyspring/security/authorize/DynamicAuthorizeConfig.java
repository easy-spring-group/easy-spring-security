package io.easyspring.security.authorize;

import io.easyspring.security.authorize.properties.DynamicAuthorizeProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 动态权限的配置类
 *
 * @author summer
 * @date 2019-03-06 17:16
 * @version V1.0.0-RELEASE
 */
@Configuration
@EnableConfigurationProperties(DynamicAuthorizeProperties.class)
public class DynamicAuthorizeConfig {
}
