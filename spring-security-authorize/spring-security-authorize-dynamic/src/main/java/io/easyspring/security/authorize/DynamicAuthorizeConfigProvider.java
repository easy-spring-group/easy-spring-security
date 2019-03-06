package io.easyspring.security.authorize;

import io.easyspring.security.authorize.constant.SecurityAuthorizeProviderLoadOrderConstant;
import io.easyspring.security.authorize.properties.DynamicAuthorizeProperties;
import io.easyspring.security.authorize.support.AuthorizePermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 动态权限的控制器实现
 *
 * @author summer
 * @date 2019-03-06 17:22
 * @version V1.0.0-RELEASE
 */
@Component
@Order(SecurityAuthorizeProviderLoadOrderConstant.LOAD_ORDER_DYNAMIC)
@Slf4j
public class DynamicAuthorizeConfigProvider implements AuthorizeConfigProvider {

    /**
     * 注入权限的配置
     */
    @Autowired
    private DynamicAuthorizeProperties dynamicAuthorizeProperties;

    /**
     * 配置动态权限忽略校验的接口
     *
     * @param config 权限校验配置对象
     * @return void
     * @author summer
     * @date 2019-03-06 17:23
     * @version V1.0.0-RELEASE
     */
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        // 获取权限的配置
        List<AuthorizePermission> ignoreAuthorizeUrls = dynamicAuthorizeProperties.getIgnoreAuthorizeUrls();

        // 如果权限配置不为空, 则执行忽略配置
        if (!CollectionUtils.isEmpty(ignoreAuthorizeUrls)) {
            // 循环执行配置
            for(AuthorizePermission authorizePermission : ignoreAuthorizeUrls) {

                // 权限的配置不能为空, 方法不能为空, 忽略的地址也不能为空
                if (authorizePermission != null
                        && authorizePermission.getMethod() != null
                        && !StringUtils.isEmpty(authorizePermission.getUrl())) {

                    // 执行忽略配置
                    config.antMatchers(
                            authorizePermission.getMethod(),
                            authorizePermission.getUrl())
                            .permitAll();
                }
            }

            log.info("SocialAuthorizeConfigProvider ignoreAuthorizes: {}", ignoreAuthorizeUrls.toString());
        }
    }
}
