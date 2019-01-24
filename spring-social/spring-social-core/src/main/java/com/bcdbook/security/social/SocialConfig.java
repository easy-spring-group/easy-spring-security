package com.bcdbook.security.social;

import com.bcdbook.security.social.properties.SocialProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 社交登录配置的配置器
 *
 * @author summer
 * @date 2019-01-24 14:27
 * @annotation @Configuration 表明这是一个配置类
 * @annotation @EnableSocial 开启 social 的使用
 * @annotation @EnableConfigurationProperties 激活配置文件(可以从 yml 中获取配置的方法)
 * @version V1.0.0-RELEASE
 */
@Configuration
@EnableSocial
@EnableConfigurationProperties(SocialProperties.class)
public class SocialConfig extends SocialConfigurerAdapter {

    /**
     * 注入数据源
     */
    @Autowired
    private DataSource dataSource;
    /**
     * 注入 social 的配置
     */
    @Autowired
    private SocialProperties socialProperties;

    /**
     * 获取社交登录存储的存储器
     * 
     * @author summer
     * @date 2019-01-24 14:27
     * @param connectionFactoryLocator connectionFactory 的查找器
     *                                 在系统中可能有多个 connectionFactory, qq 需要, 微信也会需要, 所以需要查找器进行查找
     * @return org.springframework.social.connect.UsersConnectionRepository
     * @version V1.0.0-RELEASE 
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        /*
         * 创建 jdbc 的存储器
         * dataSource: 数据源
         * connectionFactoryLocator: connectionFactory 的查找器
         *                           在系统中可能有多个 connectionFactory, qq 需要, 微信也会需要, 所以需要查找器进行查找
         * textEncryptor(Encryptors.noOpText()): 数据库存储的时候加解密的工具
         * TODO 当前没有做加解密操作, 后期需要优化
         */
        JdbcUsersConnectionRepository repository = 
                new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());

        // 设置数据库表的前缀
        // TODO: 2019-01-24 需要抽取成常量 
        repository.setTablePrefix("easy_spring_");

        // 返回封装好的存储器
        return repository;
    }

    /**
     * Social 的配置器, 用于添加自定义的 social 过滤器到过滤器链上
     *
     * @author summer
     * @date 2019-01-24 15:25
     * @return org.springframework.social.security.SpringSocialConfigurer
     * @version V1.0.0-RELEASE
     */
    @Bean
    public SpringSocialConfigurer easySpringSocialConfigurer() {
        // 获取社交登录的过滤地址
        String filterProcessUrl = socialProperties.getFilterProcessesUrl();
        // 创建自定义的 social 配置, 并修改过滤地址
        EasySpringSocialConfigurer configurer = new EasySpringSocialConfigurer(filterProcessUrl);
        return configurer;
    }

    /**
     * 配置身份验证时, 用户 id 的来源
     *
     * @author summer
     * @date 2019-01-24 16:06
     * @return org.springframework.social.UserIdSource
     * @version V1.0.0-RELEASE
     */
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

}
