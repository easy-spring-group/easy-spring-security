package io.easyspring.security.core.properties.code;

import io.easyspring.security.core.validate.code.ValidateCodeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 验证码拦截的请求信息
 *
 * @author summer
 * @version V1.0.0-RELEASE
 * DateTime 2019-01-23 13:27
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ValidateCodeFilterUrlProperties {

    /**
     * 需要拦截的地址
     */
    private String url;
    /**
     * 需要拦截地址的请求方式
     */
    private RequestMethod method;
    /**
     * 当前拦截需要什么样的校验(短信/图片验证码)
     */
    private ValidateCodeType validateCodeType;
}
