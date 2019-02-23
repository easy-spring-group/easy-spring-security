package io.easyspring.security.social.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * social 的配置参数类
 *
 * @author summer
 * @date 2019-01-24 15:09
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "easy-spring.security.social")
public class SocialProperties {

    /**
     * qq 的配置类
     */
    private QqProperties qq = new QqProperties();
    /**
     * 微信的配置类
     */
    private WeChatProperties weChat = new WeChatProperties();
    /**
     * 过滤器拦截的路径
     */
    private String filterProcessesUrl = SocialConstant.DEFAULT_FILTER_PROCESSES_URL;
    /**
     * 默认的注册地址
     */
    private String signUpUrl = SocialConstant.DEFAULT_SIGN_UP_URL;
}
