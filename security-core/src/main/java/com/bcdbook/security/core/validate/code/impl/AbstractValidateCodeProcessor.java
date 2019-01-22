package com.bcdbook.security.core.validate.code.impl;

import com.bcdbook.security.core.validate.code.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
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
     * 注入验证码的存储器
     */
    @Autowired
    private ValidateCodeRepository validateCodeRepository;

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
     * 验证码的校验器
     *
     * @author summer
     * @date 2019-01-17 14:03
     * @param request servlet 的请求信息
     * @return void
     * @version V1.0.0-RELEASE
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

        // 获取验证码的类型
        ValidateCodeType codeType = getValidateCodeType(request);

        // 获取验证码的方法
        C codeInSession = (C) validateCodeRepository.get(request, codeType);

        String codeInRequest;
        try {
            // 从请求中获取
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    codeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        // 如果前端传入的验证码的值为空
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(codeType + "请填写验证码");
        }
        // 如果系统中存储的验证码为空
        if (codeInSession == null) {
            throw new ValidateCodeException(codeType + "验证码不存在");
        }
        // 如果验证码已经过期
        if (codeInSession.isExpired()) {
            // 删除原有的验证码
            validateCodeRepository.remove(request, codeType);
            // 抛出异常
            throw new ValidateCodeException(codeType + "验证码已过期，请重新获取");
        }

        // 如果验证码不正确
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码不正确");
        }

        // 如果以上均没有抛出异常, 则说明验证通过, 此处删除缓存中的验证码
        validateCodeRepository.remove(request, codeType);
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
        String type = getValidateCodeType(request).toString().toLowerCase();
        // 获取生成器的名字
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();

        // 根据生成器的名字获取生成器对象
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        // 如果生成器不存在
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }

        // 根据上面实例化的生成器执行生成并返回生成后的验证码
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
        // 创建验证码对象
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        // 使用存储器, 存储验证码
        validateCodeRepository.save(request, code, getValidateCodeType(request));
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
     * @date 2019-01-17 14:05
     * @param request Servlet 请求信息
     * @return com.bcdbook.security.code.ValidateCodeType
     * @version V1.0.0-RELEASE
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        /*
         * 根据实例化的类名, 截取类名的前缀
         * 并根据前缀去匹配 ValidateCodeType 获取验证码类型的枚举, 并返回
         */
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }
}
