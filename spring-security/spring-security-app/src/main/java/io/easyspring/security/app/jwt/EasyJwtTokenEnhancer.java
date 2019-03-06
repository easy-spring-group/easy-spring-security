package io.easyspring.security.app.jwt;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的 jwt 增强器
 *
 * @author summer
 * @date 2019-02-22 16:29
 * @version V1.0.0-RELEASE
 */
public class EasyJwtTokenEnhancer implements TokenEnhancer {

    /**
     * jwt 增强器的增强方法
     *
     * @author summer
     * @date 2019-02-22 16:30
     * @param accessToken accessToken
     * @param authentication OAuth2 的权限校验信息
     * @return org.springframework.security.oauth2.common.OAuth2AccessToken
     * @version V1.0.0-RELEASE
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        // 创建想要添加的信息数据
        Map<String, Object> info = new HashMap<>(2);

        info.put("company", "easy-spring");

        // 设置到附加信息中
        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);

        // 返回增强后的 token
        return accessToken;
    }

}

