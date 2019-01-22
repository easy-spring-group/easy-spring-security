package com.bcdbook.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成器的抽象接口
 *
 * @author summer
 * @date 2019-01-21 18:17
 * @version V1.0.0-RELEASE
 */
public interface ValidateCodeGenerator {

    /**
     * 生成方法
     *
     * @author summer
     * @date 2019-01-16 23:49
     * @param request 请求对象
     * @return com.bcdbook.security.code.ValidateCode
     * @version V1.0.0-RELEASE
     */
	ValidateCode generate(ServletWebRequest request);

}
