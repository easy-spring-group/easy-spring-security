package io.easyspring.security.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

/**
 * APP 安全的配置类
 *
 * @author summer
 * DateTime 2019-03-06 23:21
 * @version V1.0.0-RELEASE
 */
@Configuration
public class AppSecurityConfig {


    /**
     * 创建 OAuth2 的权限安全表达式处理器
     *
     * @param applicationContext 系统配置
     * @return org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler
     * Author summer
     * DateTime 2019-03-06 23:22
     * Version V1.0.0-RELEASE
     */
    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        // 创建安全表达式处理器
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        // 设置程序的上下文环境
        expressionHandler.setApplicationContext(applicationContext);

        // 返回安全表达式处理器
        return expressionHandler;
    }
}
