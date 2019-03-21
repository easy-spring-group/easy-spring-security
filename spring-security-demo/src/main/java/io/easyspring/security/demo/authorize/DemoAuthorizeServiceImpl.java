package io.easyspring.security.demo.authorize;

import io.easyspring.security.authorize.dynamic.DynamicAuthorizeService;
import io.easyspring.security.authorize.support.AuthorizePermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


/**
 * 权限校验器
 *
 * @author summer
 * DateTime 2019-03-06 15:03
 * @version V1.0.0-RELEASE
 */
@Component("demoAuthorizeServiceImpl")
@Slf4j
public class DemoAuthorizeServiceImpl implements DynamicAuthorizeService {

    /**
     * 获取用户权限的示例方法
     *
     * @param username 用户名
     * @return java.util.Set<io.easyspring.security.authorize.support.AuthorizePermission>
     * Author summer
     * DateTime 2019-03-06 19:16
     * Version V1.0.0-RELEASE
     */
    @Override
    public Set<AuthorizePermission> getAuthorizePermission(String username) {
        Set<AuthorizePermission> authorizePermissionSet = new HashSet<>();

        AuthorizePermission authorizePermission1 = new AuthorizePermission();
        authorizePermission1.setUrl("/user/5");
        authorizePermission1.setMethod(HttpMethod.GET);

        authorizePermissionSet.add(authorizePermission1);

        return authorizePermissionSet;
    }
}
