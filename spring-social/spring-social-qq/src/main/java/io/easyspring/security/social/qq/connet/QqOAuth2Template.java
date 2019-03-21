package io.easyspring.security.social.qq.connet;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * 默认的授权信息处理模型中没有处理 http 的方法, 我们自己实现一个方法, 用于处理 qq 返回的 text/http 形式的数据
 *
 * @author summer
 * DateTime 2019-01-24 17:29
 * @version V1.0.0-RELEASE
 */
@Slf4j
public class QqOAuth2Template extends OAuth2Template {


    /**
     * 处理模板的构造方法
     *
     * Author summer
     * DateTime 2019-01-24 17:31
     * @param clientId qq 授权申请后的 id
     * @param clientSecret qq 授权申请后的密码
     * @param authorizeUrl 授权地址
     * @param accessTokenUrl 获取 token 的地址
     * Version V1.0.0-RELEASE
     */
    public QqOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        /*
         * 只有设置 UseParametersForClientAuthentication = true 以后,
         * 请求发送时才会带上 clientId 和 clientSecret
         */
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 重新构造用于获取令牌(accessToken)信息的封装
     *
     * Author summer
     * DateTime 2019-01-24 17:34
     * @param accessTokenUrl 请求 accessToken 的路径
     * @param parameters 请求参数
     * @return org.springframework.social.oauth2.AccessGrant
     * Version V1.0.0-RELEASE
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        /*
         * 发送获取 accessToken 的请求
         * accessTokenUrl: 请求地址
         * parameters: 请求参数
         * responseType: 返回类型
         */
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);

        log.info("QQ 获取 accessToke 的响应：{}", responseStr);

        // 根据返回的信息, 按照 & 截取成各个参数的字符串
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");

        // 根据 = 进行分割, 获取到对应的值
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");

        // 用自己获取到的数据封装新的 AccessGrant 对象
        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

    /**
     * 重写父类的处理返回值的模板
     * 添加一个能处理 text/http 类型的转换器
     *
     * Author summer
     * DateTime 2019-01-24 17:32
     * @return org.springframework.web.client.RestTemplate
     * Version V1.0.0-RELEASE
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
