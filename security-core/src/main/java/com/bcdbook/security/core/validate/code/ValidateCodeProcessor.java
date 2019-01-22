package com.bcdbook.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 *
 * @author summer
 * @date 2019-01-22 13:17
 * @version V1.0.0-RELEASE
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建校验码
     * 用于封装请求和相应
     *
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;

}
