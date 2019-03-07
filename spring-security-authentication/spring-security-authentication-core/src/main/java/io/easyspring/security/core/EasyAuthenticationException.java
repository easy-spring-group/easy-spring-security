package io.easyspring.security.core;

import io.easyspring.security.core.properties.SecurityConstants;
import org.springframework.security.core.AuthenticationException;

/**
 * 权限的异常类
 *
 * @author summer
 * @date 2019-01-21 18:38
 * @version V1.0.0-RELEASE
 */
public class EasyAuthenticationException extends AuthenticationException {

    private int code;

    /**
     * 构造方法
     *
     * @author summer
     * @date 2019-01-21 18:38
     * @param errorMessage 异常信息
     * @version V1.0.0-RELEASE
     */
	public EasyAuthenticationException(String errorMessage) {
		super(errorMessage);
		this.code = SecurityConstants.ErrorCode.NO_AUTHENTICATION;
	}

}
