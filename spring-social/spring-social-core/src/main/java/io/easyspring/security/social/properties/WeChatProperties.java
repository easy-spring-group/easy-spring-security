package io.easyspring.security.social.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信的参数配置类
 *
 * @author summer
 * @date 2019-01-24 15:08
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class WeChatProperties extends CommonSocialProperties {

    private static final long serialVersionUID = -1907591032820404876L;

    /**
     * 认证服务商的 id
     */
    private String providerId = SocialConstant.DEFAULT_PROVIDER_ID_WE_CHAT;
}
