package com.bcdbook.security.social.qq.config;

import com.bcdbook.security.social.properties.QqProperties;
import com.bcdbook.security.social.properties.SocialProperties;
import com.bcdbook.security.social.qq.connet.QqConnectionFactory;
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
 * QQ 登录的配置类
 * 之前使用 SocialAutoConfigurerAdapter, 但是在 Spring 2.X 中已经不存在 social 的自动配置类了
 * org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter
 *
 * @author summer
 * @date 2019-01-24 15:13
 * @annotation @Configuration 这是一个配置类
 * @annotation @ConditionalOnProperty(prefix = "easy-spring.security.social.qq", name = "app-id")
 *              当配置了 app-id 的时候才启用
 *
 * @version V1.0.0-RELEASE
 */
@Configuration
@ConditionalOnProperty(prefix = "easy-spring.security.social.qq", name = "app-id")
public class QqAutoConfiguration extends SocialConfigurerAdapter {

    /**
     * 注入 social 的相关配置
     */
    @Autowired
    private SocialProperties socialProperties;

    /**
     * 用来允许应用添加需要支持的社交网络对应的连接工厂的实现。
     * 添加 qq 的转换器的工厂 到转换器工厂的配置中
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
     * @date 2019-01-24 15:27
     * @return org.springframework.social.connect.ConnectionFactory<?>
     * @version V1.0.0-RELEASE
     */
    public ConnectionFactory<?> createConnectionFactory() {
        // 获取 qq 的相关配置
        QqProperties qq = socialProperties.getQq();
        // 通过配置构建一个 qq 的连接工厂
        return new QqConnectionFactory(qq.getProviderId(), qq.getAppId(), qq.getAppSecret());
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
