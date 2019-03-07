package io.easyspring.security.core.properties;

/**
 * 安全配置的常量
 *
 * @author summer
 * @date 2019-01-22 14:37
 * @version V1.0.0-RELEASE
 */
public interface SecurityConstants {

    /**
     * 登录相关的常量配置
     *
     * @author summer
     * @date 2019-01-23 14:33
     * @version V1.0.0-RELEASE
     */
    interface SignIn {
        /**
         * 当请求需要身份认证时，默认跳转的url
         */
        String DEFAULT_AUTHENTICATION_URL = "/authentication/require";
        /**
         * 默认的用户名密码登录请求处理url
         */
        String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/authentication/form";
        /**
         * 默认的手机验证码登录请求处理url
         */
        String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/authentication/mobile";
        /**
         * 默认登录页面
         */
        String DEFAULT_SIGN_IN_PAGE_URL = "/easy-signIn.html";
        /**
         * session 登录失效的跳转地址
         */
        String DEFAULT_SESSION_INVALID_URL = "/easy-session-invalid.html";
        /**
         * 默认的退出登录配置
         */
        String DEFAULT_SIGN_OUT_URL = "/signOut";
    }

    /**
     * 验证码相关的常量
     *
     * @author summer
     * @date 2019-01-23 14:30
     * @version V1.0.0-RELEASE
     */
    interface Validate {
        /**
         * 默认的处理验证码的 url 前缀
         */
        String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/validate/code";
        /**
         * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
         */
        String PARAMETER_NAME_CODE_IMAGE = "imageCode";
        /**
         * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
         */
        String PARAMETER_NAME_CODE_SMS = "smsCode";
        /**
         * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
         */
        String PARAMETER_NAME_MOBILE = "mobile";
        /**
         * 发送短信验证码时设定的模板的参数名称
         */
        String PARAMETER_NAME_TEMPLATE = "template";
        /**
         * 验证码的有效时长(单位是秒)
         */
        long DEFAULT_EXPIRE = 300;
        /**
         * 默认的使用 Redis 存储验证码时在 head 中传参的 key
         */
        String DEFAULT_HEADER_DEVICE_ID_KEY = "deviceId";
        /**
         * deviceId 的有效时长
         */
        int DEFAULT_DEVICE_ID_EXPIRE = 300;
    }

    /**
     * 社交登录的常量配置
     *
     * @author summer
     * @date 2019-02-20 20:42
     * @version V1.0.0-RELEASE
     */
    interface Social {
        /**
         * 默认的 OPENID 登录请求处理 url
         */
        String DEFAULT_LOGIN_PROCESSING_URL_OPENID = "/authentication/openid";
        /**
         * openid 参数名
         */
        String DEFAULT_PARAMETER_NAME_OPENID_NAME = "openId";
        /**
         * providerId 参数名
         */
        String DEFAULT_PARAMETER_NAME_PROVIDER_ID_NAME = "providerId";
        /**
         * 默认的使用 Redis 存储第三方认证信息时在 head 中传参的 key
         */
        String DEFAULT_HEADER_DEVICE_ID_KEY = "deviceId";
        /**
         * deviceId 的有效时长
         */
        int DEFAULT_DEVICE_ID_EXPIRE = 300;
        /**
         * APP 完成了第三方授权后, 若后台数据库中没有对应的用户, 执行跳转的路径
         */
        String SIGN_UP_URL = "/social/signUp";

    }

    /**
     * 安全校验的错误码常量
     *
     * @author summer
     * @date 2019-01-23 14:29
     * @version V1.0.0-RELEASE
     */
    interface ErrorCode {
        Integer NO_AUTHENTICATION = 6000;
    }

}
