package com.bcdbook.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码的异常类
 *
 * @author summer
 * @date 2019-01-21 18:38
 * @version V1.0.0-RELEASE
 */
public class ValidateCodeException extends AuthenticationException {

	private static final long serialVersionUID = -7285211528095468156L;

    /**
     * 构造方法
     *
     * @author summer
     * @date 2019-01-21 18:38
     * @param msg 异常信息
     * @version V1.0.0-RELEASE
     */
	public ValidateCodeException(String msg) {
		super(msg);
	}

}
