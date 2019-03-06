package io.easyspring.security.core.properties.browser.enums;

/**
 * 退出登录成功后返回类型的枚举类
 *
 * @author summer
 * @date 2019-02-19 14:21
 * @version V1.0.0-RELEASE
 */
public enum SignOutSuccessResultTypeEnum {
    /**
     * 自动
     * 根据请求方式进行设定(如果是 json 请求, 则返回 json, 如果是 html 请求则返回 html)
     */
    AUTO,
    /**
     * 一直返回 json
     */
    JSON,
    /**
     * 一直返回 html
     * 需要配置成功后的 html 属性
     * easy-spring.security.browser.sign-out.sign-out-success-url
     */
    HTML,
    ;
}
