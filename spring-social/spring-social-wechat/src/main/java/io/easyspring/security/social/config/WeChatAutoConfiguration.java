package io.easyspring.security.social.config;

import io.easyspring.security.social.EasyConnectView;
import io.easyspring.security.social.SocialConfig;
import io.easyspring.security.social.connect.WeChatConnectionFactory;
import io.easyspring.security.social.properties.SocialProperties;
import io.easyspring.security.social.properties.WeChatProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.servlet.View;

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
     * {@link SocialConfig#getUsersConnectionRepository(ConnectionFactoryLocator)}
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

    /**
     * 把微信的绑定和解绑视图注入到 spring 容器中
     * 用于封装返回的视图信息
     * 用户可以通过注入 wechatConnectedView 的 bean 来替换掉默认的视图
     * TODO 此处 bean 的名字后期需要调整
     *
     * @author summer
     * @date 2019-01-28 15:43
     * @return org.springframework.web.servlet.View
     * @version V1.0.0-RELEASE
     */
    @Bean({"connect/weixinConnect", "connect/weixinConnected"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View wechatConnectedView() {
        // 返回通用的的绑定视图
        return new EasyConnectView();
    }

}
