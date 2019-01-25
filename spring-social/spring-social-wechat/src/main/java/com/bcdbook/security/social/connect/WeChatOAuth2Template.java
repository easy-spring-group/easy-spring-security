package com.bcdbook.security.social.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 授权信息处理的模型
 *
 * @author summer
 * @date 2019-01-25 17:21
 * @version V1.0.0-RELEASE
 */
@Slf4j
public class WeChatOAuth2Template extends OAuth2Template {

    /**
     * 客户在微信注册后, 微信分配的客户 id
     */
    private String clientId;
    /**
     * 授权项目的密码
     */
    private String clientSecret;
    /**
     * 获取 accessToken 的地址
     */
    private String accessTokenUrl;

    /**
     * 刷新 Token 的地址
     */
    private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

    /**
     * 处理模板的构造方法
     *
     * @author summer
     * @date 2019-01-25 17:23
     * @param clientId 项目注册后的 id
     * @param clientSecret 授权项目的密码
     * @param authorizeUrl 授权地址
     * @param accessTokenUrl 获取 accessToken 的地址
     * @version V1.0.0-RELEASE
     */
    public WeChatOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        // 先调用父类的构造方法
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        // 只有设置 UseParametersForClientAuthentication = true 以后, 请求发送时才会带上 clientId 和 clientSecret
        setUseParametersForClientAuthentication(true);
        /*
         * 设置本对象中的信息
         */
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }

    /**
     * 授权访问的参数封装
     *
     * @author summer
     * @date 2019-01-25 17:26
     * @param authorizationCode 授权码
     * @param redirectUri 回调地址
     * @param parameters 请求中需要的参数信息
     * @return org.springframework.social.oauth2.AccessGrant
     * @version V1.0.0-RELEASE
     */
    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri,
                                         MultiValueMap<String, String> parameters) {
        StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);

        accessTokenRequestUrl.append("?appid="+clientId);
        accessTokenRequestUrl.append("&secret="+clientSecret);
        accessTokenRequestUrl.append("&code="+authorizationCode);
        accessTokenRequestUrl.append("&grant_type=authorization_code");
        accessTokenRequestUrl.append("&redirect_uri="+redirectUri);

        return getAccessToken(accessTokenRequestUrl);
    }

    /**
     * 刷新 token 的方法
     * 需要重新封装参数
     *
     * @author summer
     * @date 2019-01-25 17:27
     * @param refreshToken refreshToken
     * @param additionalParameters 额外的参数
     * @return org.springframework.social.oauth2.AccessGrant
     * @version V1.0.0-RELEASE
     */
    @Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {

        StringBuilder refreshTokenUrl = new StringBuilder(REFRESH_TOKEN_URL);

        refreshTokenUrl.append("?appid="+clientId);
        refreshTokenUrl.append("&grant_type=refresh_token");
        refreshTokenUrl.append("&refresh_token="+refreshToken);

        return getAccessToken(refreshTokenUrl);
    }

    /**
     * 获取 accessToken 的方法
     * 需要重新封装参数
     *
     * @author summer
     * @date 2019-01-25 17:27
     * @param accessTokenRequestUrl 获取 accessToken 的地址
     * @return org.springframework.social.oauth2.AccessGrant
     * @version V1.0.0-RELEASE
     */
    @SuppressWarnings("unchecked")
    private AccessGrant getAccessToken(StringBuilder accessTokenRequestUrl) {

        log.info("获取access_token, 请求URL: "+accessTokenRequestUrl.toString());

        String response = getRestTemplate().getForObject(accessTokenRequestUrl.toString(), String.class);

        log.info("获取access_token, 响应内容: "+response);

        Map<String, Object> result = null;
        try {
            result = new ObjectMapper().readValue(response, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回错误码时直接返回空
        if(StringUtils.isNotBlank(MapUtils.getString(result, "errcode"))){
            String errcode = MapUtils.getString(result, "errcode");
            String errmsg = MapUtils.getString(result, "errmsg");
            throw new RuntimeException("获取access token失败, errcode:"+errcode+", errmsg:"+errmsg);
        }

        WeChatAccessGrant accessToken = new WeChatAccessGrant(
                MapUtils.getString(result, "access_token"),
                MapUtils.getString(result, "scope"),
                MapUtils.getString(result, "refresh_token"),
                MapUtils.getLong(result, "expires_in"));

        // 设置额外返回的 openId 字段
        accessToken.setOpenId(MapUtils.getString(result, "openid"));

        return accessToken;
    }

    /**
     * 构建获取授权码的请求。也就是引导用户跳转到微信的地址。
     *
     * @author summer
     * @date 2019-01-25 17:28
     * @param parameters
     * @return java.lang.String
     * @version V1.0.0-RELEASE
     */
    @Override
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        String url = super.buildAuthenticateUrl(parameters);
        url = url + "&appid="+clientId+"&scope=snsapi_login";
        return url;
    }

    /**
     * 构建获取授权的地址
     *
     * @author summer
     * @date 2019-01-25 17:28
     * @param parameters
     * @return java.lang.String
     * @version V1.0.0-RELEASE
     */
    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthenticateUrl(parameters);
    }

    /**
     * 微信返回的 contentType 是 html/text，添加相应的 HttpMessageConverter 来处理。
     *
     * @author summer
     * @date 2019-01-25 17:29
     * @return org.springframework.web.client.RestTemplate
     * @version V1.0.0-RELEASE
     */
    @Override
    protected RestTemplate createRestTemplate() {
        // 通过父类构建一个模板
        RestTemplate restTemplate = super.createRestTemplate();
        // 添加新的转换器
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        // 返回修改后的模板
        return restTemplate;
    }

}
