package com.bcdbook.security.core.validate.code.impl;

import com.bcdbook.security.core.validate.code.ValidateCode;
import com.bcdbook.security.core.validate.code.ValidateCodeGenerator;
import com.bcdbook.security.core.validate.code.ValidateCodeProcessor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 抽象的验证码处理器
 *
 * @author summer
 * @date 2019-01-22 13:19
 * @version V1.0.0-RELEASE
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 操作 session 的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    /**
     * 利用 Spring 的依赖查找方式处理
     * spring 看到这样一个 map 注入的时候, 会查找 value(ValidateCodeGenerator) 的实现,
     * 并以实现类的名字作为 key 放到此 map 中
     *
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    /**
     * 抽象的创建验证码的方法
     *
     * @author summer
     * @date 2019-01-17 13:41
     * @param request servlet 请求信息
     * @throws Exception 验证码发送过程中可能出现的异常
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void create(ServletWebRequest request) throws Exception {
        // 生成验证码
        C validateCode = generate(request);
        // 执行保存
        save(request, validateCode);
        // 执行发送
        send(request, validateCode);
    }

    /**
     * 生成校验码的抽象实现
     *
     * @author summer
     * @date 2019-01-22 13:21
     * @param request 请求和相应信息
     * @return C
     * @version V1.0.0-RELEASE
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        // 获取验证码类型
        String type = getProcessorType(request);
        // 根据验证码类型从 ValidateCodeGenerator 收集器中获取对应的验证码生成器
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + "CodeGenerator");
        // 执行验证码的生成
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 验证码的保存方法
     *
     * @author summer
     * @date 2019-01-22 13:32
     * @param request 请求及响应信息
     * @param validateCode 验证码对象
     * @return void
     * @version V1.0.0-RELEASE
     */
    private void save(ServletWebRequest request, C validateCode) {
        // 把验证码存储到 session 中
        sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase(),
                validateCode);
    }

    /**
     * 发送验证码的方法
     *
     * @author summer
     * @date 2019-01-17 13:45
     * @param request servlet 请求信息
     * @param validateCode 验证码
     * @throws Exception 发送过程中出现的异常
     * @return void
     * @version V1.0.0-RELEASE
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 根据请求的 url 获取校验码的类型
     * 请求时会传入时什么类型(图片/短信)的验证码
     *
     * @author summer
     * @date 2019-01-22 13:26
     * @param request 请求及响应信息
     * @return java.lang.String
     * @version V1.0.0-RELEASE
     */
    private String getProcessorType(ServletWebRequest request) {
        // 从请求地址中截取后半段的信息, 作为请求类型返回
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }

}
