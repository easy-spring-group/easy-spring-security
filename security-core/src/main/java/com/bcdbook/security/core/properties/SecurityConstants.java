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
     * 默认的处理验证码的 url 前缀
     */
    String VALIDATE_CODE_URL_PREFIX = "/validate/code";
    /**
     * 当请求需要身份认证时，默认跳转的url
     */
    String AUTHENTICATION_URL = "/authentication/require";
    /**
     * 默认的用户名密码登录请求处理url
     */
    String SIGN_IN_PROCESSING_URL_FORM = "/authentication/form";
    /**
     * 默认的手机验证码登录请求处理url
     */
    String SIGN_IN_PROCESSING_URL_MOBILE = "/authentication/mobile";
    /**
     * 默认登录页面
     */
    String LOGIN_PAGE_URL = "/easy-signIn.html";
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

    interface ErrorCode {
        Integer NO_AUTHENTICATION = 6000;
    }

}
