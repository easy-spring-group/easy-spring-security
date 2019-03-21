package io.easyspring.security.authorize.constant;

/**
 * 权限校验的 Provider 加载顺序的配置常量
 *
 * @author summer
 * DateTime 2019-03-06 10:09
 * @version V1.0.0-RELEASE
 */
public interface SecurityAuthorizeProviderLoadOrderConstant {

    /**
     * 起始点
     */
    int LOAD_ORDER_START = 0;

    /**
     * 核心加载顺序
     */
    int LOAD_ORDER_CORE = LOAD_ORDER_START;
    /**
     * 服务的加载顺序(Browser 或 APP)
     */
    int LOAD_ORDER_SERVER = LOAD_ORDER_START + 10;
    /**
     * 社交登录权限加载顺序
     */
    int LOAD_ORDER_SOCIAL = LOAD_ORDER_START + 20;
    /**
     * 动态权限配置的加载顺序
     */
    int LOAD_ORDER_DYNAMIC = LOAD_ORDER_START + 30;
    /**
     * 用户权限加载顺序
     */
    int LOAD_ORDER_TERMINAL = LOAD_ORDER_START + 40;
}
