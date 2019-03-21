package io.easyspring.security.core.properties.browser;

import io.easyspring.security.core.properties.browser.enums.SignOutSuccessResultTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 退出登录的配置
 *
 * @author summer
 * DateTime 2019-02-19 12:54
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
public class SignOutProperties {
    /**
     * 返回的数据类型
     * 默认是自动类型
     */
    private SignOutSuccessResultTypeEnum resultType;
    /**
     * 退出成功后跳转的地址
     */
    private String signOutSuccessUrl = "/easy-sign-out.html";
    /**
     * 退出成功后需要清除的 cookie
     */
    private String[] deleteCookies;

}
