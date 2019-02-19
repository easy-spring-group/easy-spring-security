package com.bcdbook.security.browser.validate.code.impl;

import com.bcdbook.security.core.validate.code.ValidateCode;
import com.bcdbook.security.core.validate.code.ValidateCodeRepository;
import com.bcdbook.security.core.validate.code.ValidateCodeType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 基于 session 的验证码存取器
 * 当没有其存取器存在的时候, 使用该存取器
 *
 * @author summer
 * @date 2019-01-23 15:24
 * @version V1.0.0-RELEASE
 */
@Component
@ConditionalOnProperty(prefix = "easy-spring.security.code", name = "repository", havingValue = "session")
public class SessionValidateCodeRepository implements ValidateCodeRepository {
	/**
	 * 验证码放入 session 时的前缀
	 */
	private static final String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

	/**
	 * 操作 session 的工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 保存验证码的方法
     *
     * @author summer
     * @date 2019-01-17 14:14
     * @param request Servlet 请求信息
     * @param code 抽象的验证码对象
     * @param validateCodeType 验证码类型
     * @return void
     * @version V1.0.0-RELEASE
     */
	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
		sessionStrategy.setAttribute(request, getSessionKey(request, validateCodeType), code);
	}

    /**
     * 构建验证码放入 session 时的 key
     *
     * @author summer
     * @date 2019-01-22 21:39
     * @param request 请求及响应信息
     * @param validateCodeType 验证码类型
     * @return java.lang.String
     * @version V1.0.0-RELEASE
     */
	private String getSessionKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
		return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
	}

    /**
     * 获取验证码的方法
     *
     * @author summer
     * @date 2019-01-17 14:15
     * @param request Servlet 请求对象
     * @param validateCodeType 验证码类型
     * @return com.bcdbook.security.core.validate.code.ValidateCode
     * @version V1.0.0-RELEASE
     */
	@Override
	public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String sessionKey = getSessionKey(request, validateCodeType);
        return (ValidateCode) sessionStrategy.getAttribute(request, sessionKey);
	}

    /**
     * 删除缓存中验证码的方法
     *
     * @author summer
     * @date 2019-01-17 14:33
     * @param request Servlet 请求对象
     * @param codeType 验证码类型
     * @return void
     * @version V1.0.0-RELEASE
     */
	@Override
	public void remove(ServletWebRequest request, ValidateCodeType codeType) {
		sessionStrategy.removeAttribute(request, getSessionKey(request, codeType));
	}

}
