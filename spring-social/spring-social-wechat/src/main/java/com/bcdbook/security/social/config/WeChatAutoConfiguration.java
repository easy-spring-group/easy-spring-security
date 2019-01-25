package com.bcdbook.security.social.config;

import com.bcdbook.security.social.connect.WeChatConnectionFactory;
import com.bcdbook.security.social.properties.SocialProperties;
import com.bcdbook.security.social.properties.WeChatProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * 微信登录的配置类
 *
 * @author summer
 * @date 2019-01-25 16:28
 * @version V1.0.0-RELEASE
 */
@Configuration
@ConditionalOnProperty(prefix = "easy-spring.security.social.we-chat", name = "app-id")
public class WeChatAutoConfiguration extends SocialConfigurerAdapter {

    /**
     * 注入 social 的相关配置
     */
    @Autowired
    private SocialProperties socialProperties;

    /**
     * 用来允许应用添加需要支持的社交网络对应的连接工厂的实现。
     * 添加微信的转换器的工厂 到转换器工厂的配置中
     *
     * @author summer
     * @date 2019-01-24 15:17
     * @param configurer 转换器工厂的配置类
     * @param environment 环境信息
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
                                       Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }

    /**
     * 创建连接工厂的方法
     *
     * @author summer
     * @date 2019-01-25 17:02
     * @return org.springframework.social.connect.ConnectionFactory<?>
     * @version V1.0.0-RELEASE
     */
    public ConnectionFactory<?> createConnectionFactory() {
        WeChatProperties weChatConfig = socialProperties.getWeChat();
        return new WeChatConnectionFactory(weChatConfig.getProviderId(), weChatConfig.getAppId(),
                weChatConfig.getAppSecret());
    }

    /**
     * Connection 的存储器
     * 真正起作用的存储器在
     * {@link com.bcdbook.security.social.SocialConfig#getUsersConnectionRepository(ConnectionFactoryLocator)}
     *
     * 这里需要返回 null，否则会返回内存的 ConnectionRepository
     *
     * @author summer
     * @date 2019-01-24 15:28
     * @param connectionFactoryLocator 存储器的加载器
     * @return org.springframework.social.connect.UsersConnectionRepository
     * @version V1.0.0-RELEASE
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return null;
    }
}
