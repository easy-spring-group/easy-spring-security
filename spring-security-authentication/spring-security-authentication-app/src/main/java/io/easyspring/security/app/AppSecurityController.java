package io.easyspring.security.app;

import io.easyspring.security.app.social.AppSignUpUtils;
import io.easyspring.security.core.properties.SecurityConstants;
import io.easyspring.security.social.support.SocialUserInfo;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * APP 社交登录的控制器
 *
 * @author summer
 * DateTime 2019-02-21 16:58
 * @version V1.0.0-RELEASE
 */
@RestController
public class AppSecurityController {

    /**
     * Spring social 的工具类
     * 用于从 session 中获取用户的认证信息
     */
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 注入 APP 登录的工具类
     */
    @Autowired
    private AppSignUpUtils appSignUpUtils;

    /**
     * APP 环境下, 社交登录的注册方法
     * 若服务提供商通过了认证, 但是本地却没有注册, 则会直接跳到此路径下
     * 此方法直接返回用户详情信息, 同时生成 deviceId 并放到 header 中
     *
     * Author summer
     * DateTime 2019-02-21 17:01
     * @param request 请求信息
     * @return io.easyspring.security.social.support.SocialUserInfo
     * Version V1.0.0-RELEASE
     */
    @GetMapping(SecurityConstants.Social.SIGN_UP_URL)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request, HttpServletResponse response) {
        /*
         * 封装社交登录的用户详情信息
         */
        SocialUserInfo userInfo = new SocialUserInfo();
        // 从 session  中获取用户的 connection
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        // 设置服务提供商 id
        userInfo.setProviderId(connection.getKey().getProviderId());
        // 设置认证用户的用户 id
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        // 设置昵称
        userInfo.setNickname(connection.getDisplayName());
        // 设置头像
        userInfo.setHeadImage(connection.getImageUrl());

        // 创建 servletWebRequest 对象
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
        // 生成 deviceId 并设置到 servletWebRequest
        buildServletWebRequest(servletWebRequest);

        // 保存用户的认证信息到 Redis 中
        appSignUpUtils.saveConnectionData(servletWebRequest, connection.createData());

        return userInfo;
    }

    /**
     * 对 servletWebRequest 进行处理,
     * 生成 deviceId 并设置到 servletWebRequest
     *
     * Author summer
     * DateTime 2019-02-20 19:28
     * @param servletWebRequest 请求和返回信息
     * @return java.lang.String
     * Version V1.0.0-RELEASE
     */
    private String buildServletWebRequest(ServletWebRequest servletWebRequest) {
        // 定义 deviceId
        String deviceId = "";

        // 生成新的 deviceId
        deviceId = RandomStringUtils.randomAlphabetic(20);
        // 设置到 servletWebRequest 中
        servletWebRequest.setAttribute(SecurityConstants.Social.DEFAULT_HEADER_DEVICE_ID_KEY,
                deviceId,
                SecurityConstants.Social.DEFAULT_DEVICE_ID_EXPIRE);
        // 设置到 response 中
        servletWebRequest.getResponse().setHeader(SecurityConstants.Social.DEFAULT_HEADER_DEVICE_ID_KEY,
                deviceId);

        // 返回生成的 deviceId
        return deviceId;
    }

}
