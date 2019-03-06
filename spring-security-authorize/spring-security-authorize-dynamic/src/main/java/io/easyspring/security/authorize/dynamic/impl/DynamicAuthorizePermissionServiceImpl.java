package io.easyspring.security.authorize.dynamic.impl;

import io.easyspring.security.authorize.dynamic.DynamicAuthorizePermissionService;
import io.easyspring.security.authorize.dynamic.DynamicAuthorizeService;
import io.easyspring.security.authorize.support.AuthorizePermission;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 动态权限校验的实现类
 *
 * @author summer
 * @date 2019-03-06 14:09
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
@Slf4j
public class DynamicAuthorizePermissionServiceImpl implements DynamicAuthorizePermissionService {

    /**
     * 注入 url 匹配器
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 动态权限的 Service
     */
    private DynamicAuthorizeService dynamicAuthorizeService;

    /**
     * 动态权限匹配器
     *
     * @param request 请求对象
     * @param authentication 权限对象
     * @return boolean
     * @author summer
     * @date 2019-03-06 16:34
     * @version V1.0.0-RELEASE
     */
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        log.warn("DynamicAuthorize verify hasPermission, requestURI: {}, requestUrl: {}, requestMethod: {}",
                request.getRequestURI(), request.getRequestURL(), request.getMethod());

        // 获取用户鉴定主体
        Object principal = authentication.getPrincipal();

        /*
         * 获取用户名
         */
        // 定义用户名
        String username = null;

        // 如果认证主体是用户详情对象
        if (principal instanceof UserDetails) {
            // 获取用户名
            username = ((UserDetails)principal).getUsername();
            // 如果认证主体就是字符串(OAuth2 授权后认证主体就是用户名)
        } else if (principal instanceof String) {
            username = (String) principal;
        }

        /*
         * 封装用户拥有的权限信息
         */
        // 定义用户拥有的权限信息
        Set<AuthorizePermission> authorizePermissionSet = null;
        // 如果动态权限的 Service 不为空, 同时用户名不为空
        if (dynamicAuthorizeService != null && !StringUtils.isEmpty(username)) {
            // 获取用户所拥有的动态权限
            authorizePermissionSet = dynamicAuthorizeService.getAuthorizePermission(username);
        } else {
            return false;
        }

        /*
         * 执行权限校验
         */
        return hasPermission(request, authorizePermissionSet);
    }

    /**
     * 具体执行权限匹配校验的方法
     *
     * @param request 请求对象
     * @param authorizePermissionSet 权限的 set 集合
     * @return boolean
     * @author summer
     * @date 2019-03-06 23:38
     * @version V1.0.0-RELEASE
     */
    private boolean hasPermission(HttpServletRequest request, Set<AuthorizePermission> authorizePermissionSet) {
        // 参数校验, 如果传入的参数为空, 则直接返回 false
        if (request == null || CollectionUtils.isEmpty(authorizePermissionSet)) {
            return false;
        }

        // 定义返回值
        boolean hasPermission = false;

        // 循环匹配
        for(AuthorizePermission authorizePermission : authorizePermissionSet){

            // 权限校验对象的为空校验
            if (authorizePermission == null) {
                continue;
            }

            // 获取权限校验对象的相对路径
            String authorizePermissionUrl = authorizePermission.getUrl();
            // 获取权限校验对象的请求方法
            HttpMethod authorizePermissionMethod = authorizePermission.getMethod();

            log.info("DynamicAuthorize authorizePermissionUrl: {}, authorizePermissionMethod: {}",
                    authorizePermissionUrl, authorizePermissionMethod);
            log.info("DynamicAuthorize request.getRequestURI: {}, request.getMethod: {}",
                    request.getRequestURI(), request.getMethod());

            // 权限匹配数据为空, 则直接跳出循环
            if (StringUtils.isEmpty(authorizePermissionUrl) || authorizePermissionMethod == null) {
                continue;
            }

            // 地址是否匹配
            boolean urlMatch = antPathMatcher.match(authorizePermissionUrl, request.getRequestURI());
            // 方法是否匹配
            boolean methodMatch = request.getMethod().equalsIgnoreCase(authorizePermissionMethod.toString());

            log.info("DynamicAuthorize urlMatch: {}, methodMatch: {}", urlMatch, methodMatch);

            // 如果地址和方法都匹配, 则返回 true
            if (urlMatch && methodMatch) {
                hasPermission = true;
                break;
            }
        }

        return hasPermission;
    }

}
