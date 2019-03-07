package io.easyspring.security.social.connect;

import io.easyspring.security.social.api.WeChat;
import io.easyspring.security.social.api.WeChatUserInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 微信的适配器, 把通过第三方获取的数据和标准的 social 数据之间做一个适配
 * ApiAdapter 抽象的 API 适配器, 泛型是要适配的 API 对象
 *
 * @author summer
 * @date 2019-01-25 17:11
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@AllArgsConstructor
public class WeChatAdapter implements ApiAdapter<WeChat> {

    /**
     * 微信的 openId
     */
    private String openId;

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
    public boolean test(WeChat api) {
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
    public void setConnectionValues(WeChat api, ConnectionValues values) {
        // 根据 openId 获取用户详情
        WeChatUserInfo profile = api.getUserInfo(openId);
        // 设置用户 id
        values.setProviderUserId(profile.getOpenid());
        // 设置昵称
        values.setDisplayName(profile.getNickname());
        // 设置头像
        values.setImageUrl(profile.getHeadimgurl());
    }

    /**
     * 跟 {@link WeChatAdapter#setConnectionValues} 的方法类似
     * TODO 后期实现绑定和解绑的时候再实现
     *
     * @author summer
     * @date 2019-01-24 14:20
     * @param api 需要适配的 API 数据
     * @return org.springframework.social.connect.UserProfile
     * @version V1.0.0-RELEASE
     */
    @Override
    public UserProfile fetchUserProfile(WeChat api) {
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
    public void updateStatus(WeChat api, String message) {
        //do nothing
    }

}
