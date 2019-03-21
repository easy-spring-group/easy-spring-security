package io.easyspring.security.social.qq.api;

/**
 * qq api 的抽象接口
 *
 * @author summer
 * DateTime 2019-01-24 12:17
 * @version V1.0.0-RELEASE
 */
public interface Qq {

    /**
     * 获取用户详情的方法
     *
     * Author summer
     * DateTime 2019-01-24 12:39
     * @return io.easyspring.security.social.qq.api.QQUserInfo
     * Version V1.0.0-RELEASE
     */
    QqUserInfo getUserInfo();
}
