package io.easyspring.security.core.properties.oauth;

import lombok.Data;

/**
 * OAuth2 的认证配置
 *
 * @author summer
 * DateTime 2019-02-22 11:38
 * @version V1.0.0-RELEASE
 */
@Data
public class OAuth2Properties {

    /**
     * jwt 的秘签的 key
     */
    private String jwtSigningKey = "easy-spring";

    /**
     * OAuth2 支持的认证客户的配置
     */
    private OAuth2ClientProperties[] clients = {};

}
