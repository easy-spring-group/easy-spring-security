package io.easyspring.security.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * qq api 接口的实现
 * AbstractOAuth2ApiBinding 是 spring social 定义的规则, 所有的 API 都要继承此类
 * AbstractOAuth2ApiBinding 中有 accessToken 这是认证过程中服务提供商返回的识别信息, 通过此识别信息可以换取 openId
 * 实现 qq API 接口
 *
 * @author summer
 * DateTime 2019-01-24 12:25
 * @version V1.0.0-RELEASE
 */
@Slf4j
public class QqImpl extends AbstractOAuth2ApiBinding implements Qq {

    /**
     * 获取 openId 的地址
     */
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    /**
     * 获取用户详情的地址
     */
    private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    /**
     * 用户在第三方平台上注册后, 由平台分配的唯一识别信息
     */
    private String appId;
    /**
     * 用户和第三方平台之间的唯一识别码
     */
    private String openId;

    /**
     * json 的工具类
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * QQ API 实现的构造方法
     *
     * Author summer
     * DateTime 2019-01-24 12:34
     * @param accessToken 认证过程中的验证信息
     * @param appId 项目 id
     * Version V1.0.0-RELEASE
     */
    public QqImpl(String accessToken, String appId) {
        // 调用父类的构造方法, 设置 access_token 的传递方式为参数传递
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;

        // 格式化获取 openId 的地址
        String url = String.format(URL_GET_OPENID, accessToken);
        // 发送 http 请求, 并获返回值
        String result = getRestTemplate().getForObject(url, String.class);

        log.info("QQ get openId result: {}", result);

        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    /**
     * 获取用户详情的方法
     * {@link Qq#getUserInfo()}
     *
     * Author summer
     * DateTime 2019-01-24 12:39
     * @return io.easyspring.security.social.qq.api.QQUserInfo
     * Version V1.0.0-RELEASE
     */
    @Override
    public QqUserInfo getUserInfo() {
        // 封装获取用户详情的 url
        String url = String.format(URL_GET_USER_INFO, appId, openId);
        // 发送请求, 获取到用户详情
        String result = getRestTemplate().getForObject(url, String.class);


        log.info("QQ get user info result: {}", result);

        // 封装成用户详情对象, 然后返回
        QqUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, QqUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (IOException e) {
            throw new RuntimeException("QQ 获取用户详情, 转换出错", e);
        }
    }

}
