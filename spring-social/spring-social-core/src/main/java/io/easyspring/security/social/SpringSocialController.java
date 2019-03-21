package io.easyspring.security.social;

import io.easyspring.security.social.support.SocialUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring Social 的处理器
 *
 * @author summer
 * DateTime 2019-01-25 15:19
 * @version V1.0.0-RELEASE
 */
@RestController
@Slf4j
public class SpringSocialController {

    /**
     * 注入 social 登录的工具类
     */
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 获取经过授权后的 social 返回信息
     *
     * Author summer
     * DateTime 2019-01-25 15:26
     * @param request 请求信息
     * @return io.easyspring.security.social.support.SocialUserInfo
     * Version V1.0.0-RELEASE
     */
    @GetMapping("/social/user")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request){
        // 通过 request 从 session 中获取连接信息
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));

        /*
         * 封装用户信息
         */
        // 创建用于返回的用户对象
        SocialUserInfo socialUserInfo = new SocialUserInfo();
        // 设置服务商 id
        socialUserInfo.setProviderId(connection.getKey().getProviderId());
        // 设置 openId
        socialUserInfo.setProviderUserId(connection.getKey().getProviderUserId());
        // 设置昵称
        socialUserInfo.setNickname(connection.getDisplayName());
        // 设置头像
        socialUserInfo.setHeadImage(connection.getImageUrl());

        // 返回用户详情信息
        return socialUserInfo;
    }
}
