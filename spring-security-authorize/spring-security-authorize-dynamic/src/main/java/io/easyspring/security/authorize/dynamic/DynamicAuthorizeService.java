package io.easyspring.security.authorize.dynamic;

import io.easyspring.security.authorize.support.AuthorizePermission;

import java.util.Set;

/**
 * 定义动态权限的 Service
 *
 * @author summer
 * DateTime 2019-03-06 18:39
 * @version V1.0.0-RELEASE
 */
public interface DynamicAuthorizeService {

    /**
     * 获取用户权限的示例方法
     *
     * @param username 用户名
     * @return java.util.Set<io.easyspring.security.authorize.support.AuthorizePermission>
     * Author summer
     * DateTime 2019-03-06 19:16
     * Version V1.0.0-RELEASE
     */
    Set<AuthorizePermission> getAuthorizePermission(String username);
}
