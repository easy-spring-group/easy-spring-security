package io.easyspring.security.authorize.dynamic;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 动态校验的 service
 *
 * @author summer
 * @date 2019-03-06 14:09
 * @version V1.0.0-RELEASE
 */
public interface DynamicAuthorizePermissionService {

    /**
     * 执行权限匹配
     *
     * @param request 请求对象
     * @param authentication 权限信息
     * @return boolean
     * @author summer
     * @date 2019-03-06 14:14
     * @version V1.0.0-RELEASE
     */
    boolean hasPermission(HttpServletRequest request,
                          Authentication authentication);

}
