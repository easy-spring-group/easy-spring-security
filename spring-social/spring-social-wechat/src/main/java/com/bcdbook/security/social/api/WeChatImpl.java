package com.bcdbook.security.social.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 微信 API 的实现类
 *
 * @author summer
 * @date 2019-01-25 17:35
 * @version V1.0.0-RELEASE
 */
public class WeChatImpl extends AbstractOAuth2ApiBinding implements WeChat{
    /**
     * 获取用户详情的地址
     */
    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";
    /**
     * 微信返回信息中, 如果有出现了错误, 就会返回此字段
     */
    private static final String ERROR_RESULT_CONTEXT = "errcode";

    /**
     * json 的工具类
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 微信 API 实现的构造方法
     *
     * @author summer
     * @date 2019-01-25 16:20
     * @param accessToken 认证过程中的验证信息
     * @version V1.0.0-RELEASE
     */
    public WeChatImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    /**
     * 默认注册的 StringHttpMessageConverter 字符集为 ISO-8859-1，
     * 而微信返回的是 UTF-8 的，所以覆盖了原来的方法。
     *
     * @author summer
     * @date 2019-01-25 16:22
     * @return java.util.List<org.springframework.http.converter.HttpMessageConverter<?>>
     * @version V1.0.0-RELEASE
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        // 获取父级的 Http 请求信息转换器
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        // 删除原有的
        messageConverters.remove(0);
        // 添加信息的转换器
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        // 返回修改后的转换器
        return messageConverters;
    }

    /**
     * 获取微信用户信息
     * 在微信中, 不需要用 accessToken 换取 openId, 减少了一个步骤, 我们此处要做一些处理
     *
     * @author summer
     * @date 2019-01-25 16:24
     * @param openId 用户识别码
     * @return com.bcdbook.security.social.api.WeChatUserInfo
     * @version V1.0.0-RELEASE
     */
    @Override
    public WeChatUserInfo getUserInfo(String openId) {
        // 拼装发送请求的地址
        String url = URL_GET_USER_INFO + openId;
        // 执行请求的发送
        String response = getRestTemplate().getForObject(url, String.class);
        // 检查返回信息中是否有错误信息
        if(StringUtils.contains(response, ERROR_RESULT_CONTEXT)) {
            return null;
        }
        // 定义用户详情对象
        WeChatUserInfo profile = null;
        try {
            // 把返回信息转换成微信用户信息
            profile = objectMapper.readValue(response, WeChatUserInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 执行用户信息的返回
        return profile;
    }

}
