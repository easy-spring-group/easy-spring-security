package io.easyspring.security.browser;

import io.easyspring.security.browser.logout.EasySignOutSuccessHandler;
import io.easyspring.security.browser.session.EasyExpiredSessionStrategy;
import io.easyspring.security.browser.session.EasyInvalidSessionStrategy;
import io.easyspring.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 浏览器的 bean 配置
 *
 * @author summer
 * @date 2019-02-18 18:49
 * @version V1.0.0-RELEASE
 */
@Configuration
public class BrowserSecurityBeanConfig {

    /**
     * 注入 security 的配置类
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * session 失效的 bean 的配置
     * 可以通过重写 InvalidSessionStrategy 来覆盖当前处理逻辑
     *
     * @author summer
     * @date 2019-02-18 20:54
     * @return org.springframework.security.web.session.InvalidSessionStrategy
     * @version V1.0.0-RELEASE
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new EasyInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    /**
     * session 过期的 bean 的配置
     * 可以通过重写 SessionInformationExpiredStrategy 来覆盖当前处理逻辑
     *
     * @author summer
     * @date 2019-02-18 20:55
     * @return org.springframework.security.web.session.SessionInformationExpiredStrategy
     * @version V1.0.0-RELEASE
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new EasyExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    /**
     * 退出登录成功后的处理器 配置
     *
     * @author summer
     * @date 2019-02-19 13:07
     * @return org.springframework.security.web.authentication.logout.LogoutSuccessHandler
     * @version V1.0.0-RELEASE
     */
    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new EasySignOutSuccessHandler(securityProperties.getBrowser().getSignOut().getSignOutSuccessUrl(),
                securityProperties.getBrowser().getSignOut().getResultType());
    }


}
