package com.bcdbook.security.social.qq.connet;

import com.bcdbook.security.social.qq.api.Qq;
import com.bcdbook.security.social.qq.api.QqImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * Qq Service 的管理器
 *
 * @author summer
 * @date 2019-01-24 12:54
 * @version V1.0.0-RELEASE
 */
public class QqServiceProvider extends AbstractOAuth2ServiceProvider<Qq> {

    /**
     * 项目 id
     */
    private String appId;

    /**
     * 将用户导向认证服务器时导向的 url
     */
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    /**
     * 获取 token 的接口的地址
     */
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";


    /**
     * 构造方法
     *
     * @author summer
     * @date 2019-01-24 12:59
     * @param appId 项目 id
     * @param appSecret 项目密码
     * @version V1.0.0-RELEASE
     */
    public QqServiceProvider(String appId, String appSecret) {
        /*
         * 通过项目 id, 项目密码, 授权地址, token 的获取地址, 创建一个 OAuth2 认证的模板对象
         * 并调用父类的构造方法, 构造一个 QQ 的控制器
         */
        super(new QqOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        // 设置 APP id
        this.appId = appId;

    }

    /**
     * 重写父类中获取 API 对象的方法
     *
     * @author summer
     * @date 2019-01-24 12:56
     * @param accessToken accessToken
     * @return com.bcdbook.security.social.qq.api.Qq
     * @version V1.0.0-RELEASE
     */
    @Override
    public Qq getApi(String accessToken) {
        // 因为 accessToken 具有唯一性, 所以, QqImpl 对象在项目中是非单例的, 每次获取 API 都要创建一个新的对象
        return new QqImpl(accessToken, appId);
    }

}
