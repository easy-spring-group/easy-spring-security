package com.bcdbook.security.social.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * qq 的参数配置类
 *
 * @author summer
 * @date 2019-01-24 15:08
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class QqProperties extends CommonSocialProperties {

    private static final long serialVersionUID = 6178558524546289712L;

    /**
     * 认证服务商的 id
     */
    private String providerId = SocialConstant.DEFAULT_PROVIDER_ID_QQ;
}
