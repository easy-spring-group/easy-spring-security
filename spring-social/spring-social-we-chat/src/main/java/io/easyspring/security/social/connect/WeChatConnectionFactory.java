package io.easyspring.security.social.connect;

import io.easyspring.security.social.api.WeChat;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 微信的转换器工厂
 *
 * @author summer
 * DateTime 2019-01-25 17:03
 * @version V1.0.0-RELEASE
 */
public class WeChatConnectionFactory extends OAuth2ConnectionFactory<WeChat> {

    /**
     * 构造方法
     *
     * Author summer
     * DateTime 2019-01-25 17:04
     * @param providerId 提供商的唯一标识
     * @param appId 项目 id
     * @param appSecret 项目密码
     * Version V1.0.0-RELEASE
     */
    public WeChatConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WeChatServiceProvider(appId, appSecret), new WeChatAdapter());
    }

    /**
     * 重写获取 openId 的方法
     * 由于微信的 openId 是和 accessToken 一起返回的，
     * 所以在这里直接根据 accessToken 设置 providerUserId 即可，不用像 QQ 那样通过 QQAdapter 来获取
     *
     * Author summer
     * DateTime 2019-01-25 17:55
     * @param accessGrant 微信的授权访问器
     * @return java.lang.String
     * Version V1.0.0-RELEASE
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if(accessGrant instanceof WeChatAccessGrant) {
            return ((WeChatAccessGrant)accessGrant).getOpenId();
        }
        return null;
    }

    /**
     * 创建连接的方法
     *
     * Author summer
     * DateTime 2019-01-25 17:58
     * @param accessGrant 微信的授权访问器
     * @return org.springframework.social.connect.Connection<io.easyspring.security.social.api.WeChat>
     * Version V1.0.0-RELEASE
     */
    @Override
    public Connection<WeChat> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<WeChat>(getProviderId(),
                extractProviderUserId(accessGrant),
                accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(),
                accessGrant.getExpireTime(),
                getOAuth2ServiceProvider(),
                // 默认的实现中, Adapter 只有一份, 而微信返回 accessToken token 时返回的 openId 存到了 Adapter 中
                // 所以, 这里要创建一个新的 WeChatAdapter
                getApiAdapter(extractProviderUserId(accessGrant)));
    }

    /**
     * 创建连接的方法
     *
     * Author summer
     * DateTime 2019-01-25 18:01
     * @param data 连接数据
     * @return org.springframework.social.connect.Connection<io.easyspring.security.social.api.WeChat>
     * Version V1.0.0-RELEASE
     */
    @Override
    public Connection<WeChat> createConnection(ConnectionData data) {
        return new OAuth2Connection<WeChat>(data,
                getOAuth2ServiceProvider(),
                // 同上 WeChatConnectionFactory#createConnection
                getApiAdapter(data.getProviderUserId()));
    }

    /**
     * 获取适配器的方法
     *
     * 默认的实现中, Adapter 只有一份, 而微信返回 accessToken token 时返回的 openId 存到了 Adapter 中
     * 所以, 这里要创建一个新的 WeChatAdapter
     * 这里是微信和 qq 最大不一样的地方
     *
     * Author summer
     * DateTime 2019-01-25 18:03
     * @param providerUserId openId
     * @return org.springframework.social.connect.ApiAdapter<io.easyspring.security.social.api.WeChat>
     * Version V1.0.0-RELEASE
     */
    private ApiAdapter<WeChat> getApiAdapter(String providerUserId) {
        return new WeChatAdapter(providerUserId);
    }

    /**
     * 获取授权 service 的提供商
     *
     * Author summer
     * DateTime 2019-01-25 18:03
     * @return org.springframework.social.oauth2.OAuth2ServiceProvider<io.easyspring.security.social.api.WeChat>
     * Version V1.0.0-RELEASE
     */
    private OAuth2ServiceProvider<WeChat> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WeChat>) getServiceProvider();
    }

}
