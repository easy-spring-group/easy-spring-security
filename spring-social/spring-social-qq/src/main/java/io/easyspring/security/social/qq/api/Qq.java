package io.easyspring.security.social.qq.api;

/**
 * qq api 的抽象接口
 *
 * @author summer
 * @date 2019-01-24 12:17
 * @version V1.0.0-RELEASE
 */
public interface Qq {

    /**
     * 获取用户详情的方法
     *
     * @author summer
     * @date 2019-01-24 12:39
     * @return io.easyspring.security.social.qq.api.QQUserInfo
     * @version V1.0.0-RELEASE
     */
    QqUserInfo getUserInfo();
}
