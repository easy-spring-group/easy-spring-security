package io.easyspring.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成器的抽象接口
 *
 * @author summer
 * DateTime 2019-01-21 18:17
 * @version V1.0.0-RELEASE
 */
public interface ValidateCodeGenerator {

    /**
     * 生成方法
     *
     * Author summer
     * DateTime 2019-01-16 23:49
     * @param request 请求对象
     * @return io.easyspring.security.code.ValidateCode
     * Version V1.0.0-RELEASE
     */
	ValidateCode generate(ServletWebRequest request);

}
