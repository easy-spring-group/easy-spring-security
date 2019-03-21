package io.easyspring.security.app.authentication.openid;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * OpenId 权限校验的 token
 *
 * @author summer
 * DateTime 2019-02-21 09:37
 * @version V1.0.0-RELEASE
 */
public class OpenIdAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -9058837589445630456L;

    /**
     * 主要认证信息
     */
    private final Object principal;
    /**
     * 认证供应商 id
     */
    private String providerId;

    /**
     * 认证之前的构造方法 openId
     * 执行认证之前传入 openId, 这样可以
     *
     * Author summer
     * DateTime 2019-02-21 09:42
     * @param openId 认证信息 id
     * @param providerId 认证供应商 id
     * Version V1.0.0-RELEASE
     */
    public OpenIdAuthenticationToken(String openId, String providerId) {
        super(null);
        this.principal = openId;
        this.providerId = providerId;
        setAuthenticated(false);
    }

    /**
     * 认证完成之后的构造方法
     *
     * Author summer
     * DateTime 2019-02-21 09:47
     * @param principal 认证后的用户信息
     * @param authorities 认证用户的权限信息
     * Version V1.0.0-RELEASE
     */
    public OpenIdAuthenticationToken(Object principal,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        // must use super, as we override
        super.setAuthenticated(true);
    }



    /**
     * 获取证书的方法
     *
     * Author summer
     * DateTime 2019-02-21 09:52
     * @return java.lang.Object
     * Version V1.0.0-RELEASE
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * 清除证书的方法
     *
     * Author summer
     * DateTime 2019-02-21 09:53
     * Version V1.0.0-RELEASE
     */
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    /**
     * 获取用户主要认证信息的方法
     *
     * Author summer
     * DateTime 2019-02-21 09:53
     * @return java.lang.Object
     * Version V1.0.0-RELEASE
     */
    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    /**
     * 获取认证提供商 id 的方法
     *
     * Author summer
     * DateTime 2019-02-21 09:53
     * @return java.lang.String
     * Version V1.0.0-RELEASE
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * 设置认证状态的方法,
     * 注意: 当前类中的重写方法不能直接设置认证状态为 true
     *
     * Author summer
     * DateTime 2019-02-21 09:54
     * @param isAuthenticated 是否已认证
     * Version V1.0.0-RELEASE
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }
}
