package io.easyspring.security.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 默认的权限控制操作接口
 *
 * @author summer
 * @date 2019-03-05 16:23
 * @version V1.0.0-RELEASE
 */
@Component
public class EasyAuthorizeConfigManager implements AuthorizeConfigManager {

    /**
     * 收集当前项目中所有的 provider
     * 注入 Spring 中所有的权限控制器
     */
    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviders;

    /**
     * 把所有的权限控制器, 都加上对应的配置
     *
     * @param config 配置信息
     * @return void
     * @author summer
     * @date 2019-03-05 16:25
     * @version V1.0.0-RELEASE
     */
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        // 循环处理所有的 provider, 让 config 加入后来配置的 config 数据
        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
            authorizeConfigProvider.config(config);
        }
    }

}
