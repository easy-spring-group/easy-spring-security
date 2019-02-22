package com.bcdbook.security.core.properties.oauth;

import lombok.Data;

/**
 * OAuth2 认证, 支持的客户端的配置信息
 *
 * @author summer
 * @date 2019-02-22 11:34
 * @version V1.0.0-RELEASE
 */
@Data
public class OAuth2ClientProperties {

    /**
     * 客户 id
     */
    private String clientId;
    /**
     * 客户的密码
     */
    private String clientSecret;
    /**
     * 支持的认证类型
     */
    private String[] authorizedGrantTypes = {};
    /**
     * access_token 的有效时长 (默认 2 小时)
     */
    private int accessTokenValidateSeconds = 7200;
    /**
     * refreshToken 的有效时长 (默认 7 天)
     */
    private int refreshTokenValiditySeconds = 604800;
    /**
     * 允许回调的地址
     */
    private String[] redirectUris = {};
    /**
     * 允许的授权范围
     */
    private String[] scopes = {};


}
