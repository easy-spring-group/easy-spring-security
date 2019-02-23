package io.easyspring.security.social.qq.connet;

import io.easyspring.security.social.qq.api.Qq;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * 转换器的工厂
 *
 * @author summer
 * @date 2019-01-24 14:24
 * @version V1.0.0-RELEASE
 */
public class QqConnectionFactory extends OAuth2ConnectionFactory<Qq> {

    /**
     * 构造方法
     *
     * @author summer
     * @date 2019-01-24 14:24
     * @param providerId 提供商的唯一标识
     * @param appId 项目 id
     * @param appSecret 项目密码
     * @version V1.0.0-RELEASE
     */
    public QqConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QqServiceProvider(appId, appSecret), new QqAdapter());
    }

}
