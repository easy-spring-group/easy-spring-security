package io.easyspring.security.app.social;

import io.easyspring.security.app.AppSecretException;
import io.easyspring.security.app.properties.RedisKeyPrefixConstants;
import io.easyspring.security.core.properties.SecurityConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

/**
 * APP 登录的服务类
 *
 * @author summer
 * @date 2019-02-21 15:12
 * @version V1.0.0-RELEASE
 */
@Component
public class AppSignUpUtils {

    /**
     * 注入 Redis 的模板
     */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    /**
     * 注入社交登录的存储器
     */
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;
    /**
     * 注入社交登录的工厂的加载器
     * 此加载器, 可以根据社交登录服务提供方的 id 获取对应的社交登录工厂
     */
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    /**
     * 存储社交登录的认证信息
     *
     * @author summer
     * @date 2019-02-21 15:26
     * @param request 请求对象
     * @param connectionData 用户社交登录后, 获取到的认证信息
     * @return void
     * @version V1.0.0-RELEASE
     */
    public void saveConnectionData(WebRequest request, ConnectionData connectionData) {
        /*
         * 因为 APP 登录的时候, 没有 session
         * 所以这里需要把获取到的社交登录认证信息存储到 Redis 中
         * 此处设置存储的时间为 10 分钟
         */
        redisTemplate.opsForValue().set(getKey(request), connectionData, 10, TimeUnit.MINUTES);
    }

    /**
     * 用户注册完成后, 执行绑定的方法
     * TODO 此处的 deviceId 可能需要处理
     *
     * @author summer
     * @date 2019-02-21 16:05
     * @param request 请求信息
     * @param userId 用户的 id (openId)
     * @return void
     * @version V1.0.0-RELEASE
     */
    public void doPostSignUp(WebRequest request, String userId) {
        // 从 Redis 中获取用户的认证信息
        String key = getKey(request);
        // 校验认证信息的 key 是否存在
        if(!redisTemplate.hasKey(key)){
            throw new AppSecretException("无法找到缓存的用户社交账号信息");
        }

        // 获取用户的认证信息
        ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(key);
        /*
         * 通过 connectionFactory 的加载器和服务提供商的 id 获取 connectionFactory 对象
         * 用获取到的 connectionFactory 对象创建 connection 对象
         */
        Connection<?> connection = connectionFactoryLocator
                .getConnectionFactory(connectionData.getProviderId())
                .createConnection(connectionData);
        /*
         * 根据用户 id 拿到对应的 connectionRepository
         * 给获取到的 connectionRepository 添加 Redis 中存储的认证信息
         * 此操作会存储用户的认证信息到社交登录的数据库中
         */
        usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);

        // 删除 Redis 缓存中的认证信息
        redisTemplate.delete(key);
    }

    /**
     * 获取用于存储 社交登录认证信息 的 key
     *
     * @author summer
     * @date 2019-02-21 15:34
     * @param request 请求信息
     * @return java.lang.String
     * @version V1.0.0-RELEASE
     */
    private String getKey(WebRequest request) {
        // 从请求中获取 deviceId
        String deviceId = (String) request.getAttribute(SecurityConstants.Social.DEFAULT_HEADER_DEVICE_ID_KEY,
                SecurityConstants.Social.DEFAULT_DEVICE_ID_EXPIRE);
        // 验证 deviceId
        if (StringUtils.isBlank(deviceId)) {
            throw new AppSecretException("设备id参数不能为空");
        }

        return String.format(RedisKeyPrefixConstants.SOCIAL_CONNECT_DATA_TEMPLATE, deviceId);
    }

}
