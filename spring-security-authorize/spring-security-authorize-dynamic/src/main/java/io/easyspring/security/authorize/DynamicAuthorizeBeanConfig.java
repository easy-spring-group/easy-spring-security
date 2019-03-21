package io.easyspring.security.authorize;

import io.easyspring.security.authorize.dynamic.DynamicAuthorizePermissionService;
import io.easyspring.security.authorize.dynamic.DynamicAuthorizeService;
import io.easyspring.security.authorize.dynamic.impl.DynamicAuthorizePermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 动态权限校验的 bean 配置
 *
 * @author summer
 * DateTime 2019-03-06 15:25
 * @version V1.0.0-RELEASE
 */
@Configuration
public class DynamicAuthorizeBeanConfig {

    /**
     * 注入动态权限的
     */
    @Autowired(required = false)
    private DynamicAuthorizeService dynamicAuthorizeService;

    /**
     * 动态权限校验的 bean
     *
     * @return io.easyspring.security.authorize.dynamic.DynamicAuthorizePermissionService
     * Author summer
     * DateTime 2019-03-06 15:29
     * Version V1.0.0-RELEASE
     */
    @Bean
    @ConditionalOnMissingBean(DynamicAuthorizePermissionService.class)
    public DynamicAuthorizePermissionService dynamicAuthorizePermissionService(){
        // 创建动态权限校验器
        DynamicAuthorizePermissionServiceImpl dynamicAuthorizePermissionService =
                new DynamicAuthorizePermissionServiceImpl();

        // 设置动态权限的 Service
        dynamicAuthorizePermissionService.setDynamicAuthorizeService(dynamicAuthorizeService);

        // 返回封装好的动态校验器
        return dynamicAuthorizePermissionService;
    }
}
