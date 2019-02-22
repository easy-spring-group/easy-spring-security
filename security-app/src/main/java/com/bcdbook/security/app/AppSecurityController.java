package com.bcdbook.security.app;

import com.bcdbook.security.app.social.AppSignUpUtils;
import com.bcdbook.security.social.support.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * APP 社交登录的控制器
 *
 * @author summer
 * @date 2019-02-21 16:58
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
     * TODO 此处不能直接返回用户详情信息, openId 是不能直接返回的数据, 同时需要生成 deviceId
     *
     * @author summer
     * @date 2019-02-21 17:01
     * @param request 请求信息
     * @return com.bcdbook.security.social.support.SocialUserInfo
     * @version V1.0.0-RELEASE
     */
    @GetMapping("/social/signUp")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
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

        // 保存用户的认证信息到 Redis 中
        appSignUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());

        return userInfo;
    }

}
