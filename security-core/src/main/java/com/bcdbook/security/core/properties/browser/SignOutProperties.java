package com.bcdbook.security.core.properties.browser;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 退出登录的配置
 *
 * @author summer
 * @date 2019-02-19 12:54
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
public class SignOutProperties {
    /**
     * 退出成功后跳转的地址
     */
    private String signOutSuccessUrl;
    /**
     * 退出成功后需要清除的 cookie
     */
    private String[] deleteCookies;

}
