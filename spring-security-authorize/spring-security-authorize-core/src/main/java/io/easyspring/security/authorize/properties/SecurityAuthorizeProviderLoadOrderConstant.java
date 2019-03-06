package io.easyspring.security.authorize.properties;

/**
 * 权限校验的 Provider 加载顺序的配置常量
 *
 * @author summer
 * @date 2019-03-06 10:09
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
     * 社交登录权限加载顺序
     */
    int LOAD_ORDER_SOCIAL = LOAD_ORDER_START + 10;
    /**
     * 用户权限加载顺序
     */
    int LOAD_ORDER_TERMINAL = LOAD_ORDER_START + 20;
}
