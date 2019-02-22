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
     * 创建验证码的方法
     *
     * @author summer
     * @date 2019-01-17 13:41
     * @param request servlet 请求信息
     * @throws Exception 验证码发送过程中可能出现的异常
     * @return void
     * @version V1.0.0-RELEASE
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 验证码的校验器
     *
     * @author summer
     * @date 2019-01-17 14:03
     * @param servletWebRequest servlet 的请求信息
     * @return void
     * @version V1.0.0-RELEASE
     */
    void validate(ServletWebRequest servletWebRequest);
}
