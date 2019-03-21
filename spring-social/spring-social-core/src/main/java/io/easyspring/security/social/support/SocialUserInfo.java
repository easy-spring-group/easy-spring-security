package io.easyspring.security.social.support;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Spring social 的用户详情
 *
 * @author summer
 * DateTime 2019-01-25 15:16
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
public class SocialUserInfo {
    /**
     * 服务商的 id, 用于表述是哪个第三方应用提供相关的服务
     */
    private String providerId;
    /**
     * open Id
     */
    private String providerUserId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String headImage;
}
