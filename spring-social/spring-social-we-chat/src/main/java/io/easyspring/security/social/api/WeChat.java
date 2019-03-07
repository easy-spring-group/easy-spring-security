package io.easyspring.security.social.api;

/**
 * 微信的 api 的抽象接口
 *
 * @author summer
 * @date 2019-01-25 16:14
 * @version V1.0.0-RELEASE
 */
public interface WeChat {

    /**
     * 获取微信用户信息
     * 在微信中, 不需要用 accessToken 换取 openId, 减少了一个步骤, 我们此处要做一些处理
     *
     * @author summer
     * @date 2019-01-25 16:24
     * @param openId 用户识别码
     * @return io.easyspring.security.social.api.WeChatUserInfo
     * @version V1.0.0-RELEASE
     */
    WeChatUserInfo getUserInfo(String openId);

}
