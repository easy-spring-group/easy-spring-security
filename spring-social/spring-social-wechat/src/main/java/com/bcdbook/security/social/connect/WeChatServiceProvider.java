package com.bcdbook.security.social.connect;

import com.bcdbook.security.social.api.WeChat;
import com.bcdbook.security.social.api.WeChatImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 微信 Service 的管理器
 *
 * @author summer
 * @date 2019-01-25 17:17
 * @version V1.0.0-RELEASE
 */
public class WeChatServiceProvider extends AbstractOAuth2ServiceProvider<WeChat> {
    /**
     * 微信获取授权码的url
     */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 微信获取accessToken的url
     */
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 构造方法
     *
     * @author summer
     * @date 2019-01-25 17:18
     * @param appId 项目 id
     * @param appSecret 项目密码
     * @version V1.0.0-RELEASE
     */
    public WeChatServiceProvider(String appId, String appSecret) {
        super(new WeChatOAuth2Template(appId, appSecret, URL_AUTHORIZE,URL_ACCESS_TOKEN));
    }

    /**
     * 重写父类中获取 API 对象的方法
     *
     * @author summer
     * @date 2019-01-25 17:19
     * @param accessToken accessToken
     * @return com.bcdbook.security.social.api.WeChat
     * @version V1.0.0-RELEASE
     */
    @Override
    public WeChat getApi(String accessToken) {
        return new WeChatImpl(accessToken);
    }
}
