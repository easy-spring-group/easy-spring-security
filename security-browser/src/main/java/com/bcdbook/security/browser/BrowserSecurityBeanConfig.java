package com.bcdbook.security.browser;

import com.bcdbook.security.browser.session.EasyExpiredSessionStrategy;
import com.bcdbook.security.browser.session.EasyInvalidSessionStrategy;
import com.bcdbook.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

}
