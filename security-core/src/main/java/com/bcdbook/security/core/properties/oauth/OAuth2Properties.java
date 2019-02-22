package com.bcdbook.security.core.properties.oauth;

import lombok.Data;

/**
 * OAuth2 的认证配置
 *
 * @author summer
 * @date 2019-02-22 11:38
 * @version V1.0.0-RELEASE
 */
@Data
public class OAuth2Properties {

    /**
     * OAuth2 支持的认证客户的配置
     */
    private OAuth2ClientProperties[] clients = {};

}
