package io.easyspring.security.social.connect;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.social.oauth2.AccessGrant;

/**
 * 微信的授权访问器
 *
 * @author summer
 * DateTime 2019-01-25 17:07
 * @version V1.0.0-RELEASE
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WeChatAccessGrant extends AccessGrant {

    private static final long serialVersionUID = -9111094242788738520L;

    private String openId;

    /**
     * 空参构造方法
     *
     * Author summer
     * DateTime 2019-01-25 17:08
     * Version V1.0.0-RELEASE
     */
    public WeChatAccessGrant() {
        super("");
    }

    /**
     * 多参构造方法
     *
     * Author summer
     * DateTime 2019-01-25 17:08
     * @param accessToken accessToken
     * @param scope 授权范围
     * @param refreshToken 刷新 accessToken 时用到的识别信息
     * @param expires 超时时长
     * Version V1.0.0-RELEASE
     */
    public WeChatAccessGrant(String accessToken, String scope, String refreshToken, Long expires) {
        super(accessToken, scope, refreshToken, expires);
    }

}
