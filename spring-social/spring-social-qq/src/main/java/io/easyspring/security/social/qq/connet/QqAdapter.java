package io.easyspring.security.social.qq.connet;

import io.easyspring.security.social.qq.api.Qq;
import io.easyspring.security.social.qq.api.QqUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * QQ 的适配器, 把通过第三方获取的数据和标准的 social 数据之间做一个适配
 * ApiAdapter 抽象的 API 适配器, 泛型是要适配的 API 对象
 *
 * @author summer
 * @date 2019-01-24 14:09
 * @version V1.0.0-RELEASE
 */
public class QqAdapter implements ApiAdapter<Qq> {

    /**
     * 测试连接是否正常的方法
     *
     * @author summer
     * @date 2019-01-24 14:10
     * @param api 想要适配的 API
     * @return boolean
     * @version V1.0.0-RELEASE
     */
    @Override
    public boolean test(Qq api) {
        return true;
    }

    /**
     * 在 connection 数据和 API 数据之间做适配
     *
     * @author summer
     * @date 2019-01-24 14:12
     * @param api 需要适配的 API 数据
     * @param values 输出的 social 标准的数据
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void setConnectionValues(Qq api, ConnectionValues values) {
        // 获取第三方 API 的用户详情
        QqUserInfo userInfo = api.getUserInfo();

        /*
         * 把获取到的数据设置到标准的 connection 中
         */
        // 设置用户名称, 此处设置为昵称
        values.setDisplayName(userInfo.getNickname());
        // 设置头像
        values.setImageUrl(userInfo.getFigureUrlQq1());
        // 设置主页(例如:微博中有个人主页的概念)
        values.setProfileUrl(null);
        // 设置 open id(服务商的用户 id)
        values.setProviderUserId(userInfo.getOpenId());
    }

    /**
     * 跟 {@link QqAdapter#setConnectionValues} 的方法类似
     * TODO 后期实现绑定和解绑的时候再实现
     *
     * @author summer
     * @date 2019-01-24 14:20
     * @param api 需要适配的 API 数据
     * @return org.springframework.social.connect.UserProfile
     * @version V1.0.0-RELEASE
     */
    @Override
    public UserProfile fetchUserProfile(Qq api) {
        return null;
    }

    /**
     * 更新个人信息的方法
     * 例如: 微博中有时间线和个人主页的状态的概念, 此处不做处理
     *
     * @author summer
     * @date 2019-01-24 14:22
     * @param api API 数据
     * @param message 发送的消息
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void updateStatus(Qq api, String message) {
        //do noting
    }

}
