package io.easyspring.security.authorize.properties;

import io.easyspring.security.authorize.support.AuthorizePermission;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 动态权限的相关参数配置
 *
 * @author summer
 * @date 2019-03-06 17:15
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "easy-spring.security.authorize")
public class DynamicAuthorizeProperties {

    /**
     * 忽略权限校验的地址
     */
    private List<AuthorizePermission> ignoreAuthorizeUrls;
}
