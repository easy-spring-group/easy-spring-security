package io.easyspring.security.social.properties;

/**
 * social 的常量类
 *
 * @author summer
 * @date 2019-01-24 15:05
 * @version V1.0.0-RELEASE
 */
public interface SocialConstant {
    /**
     * 默认的 qq 的服务商 id
     */
    String DEFAULT_PROVIDER_ID_QQ = "qq";
    /**
     * 默认的微信的服务商 id
     */
    String DEFAULT_PROVIDER_ID_WE_CHAT = "weChat";
    /**
     * 默认的第三方权限校验的过滤地址
     */
    String DEFAULT_FILTER_PROCESSES_URL = "/auth";
    /**
     * 默认的注册页面
     */
    String DEFAULT_SIGN_UP_URL = "/resources/easy-signUp.html";

    /**
     * 默认的 第三方账户数据库表的前缀
     */
    String DEFAULT_CONNECT_TABLE_PREFIX = "EasySpring_";
}
