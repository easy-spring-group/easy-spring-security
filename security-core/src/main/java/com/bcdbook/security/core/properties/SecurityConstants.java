package com.bcdbook.security.core.properties;

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
         * 验证码的默认存储器
         */
        String DEFAULT_REPOSITORY = "session";
        /**
         * 验证码的有效时长(单位是秒)
         */
        long DEFAULT_EXPIRE = 300;
        /**
         * 默认的使用 Redis 存储验证码时在 head 中传参的 key
         */
        String DEFAULT_HEADER_DEVICE_ID = "deviceId";
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
