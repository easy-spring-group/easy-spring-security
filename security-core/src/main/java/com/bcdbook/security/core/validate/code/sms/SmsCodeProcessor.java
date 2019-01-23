package com.bcdbook.security.core.validate.code.sms;

import com.bcdbook.security.core.validate.code.ValidateCode;
import com.bcdbook.security.core.properties.SecurityConstants;
import com.bcdbook.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码处理器(实现抽象的验证码处理器, 并重写发送验证码的方法)
 *
 * @author summer
 * @date 2019-01-17 13:32
 * @version V1.0.0-RELEASE
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

	/**
	 * 短信验证码发送器
	 */
	@Autowired
	private SmsCodeSender smsCodeSender;
	
    /**
     * 发送短信的方法
     *
     * @author summer
     * @date 2019-01-17 13:35
     * @param request 请求信息
     * @param validateCode 验证码对象
     * @return void
     * @version V1.0.0-RELEASE
     */
	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode)
            throws ServletRequestBindingException {

	    // 获取发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
		String paramName = SecurityConstants.Validate.PARAMETER_NAME_MOBILE;
		// 从请求中, 根据约定好的手机号参数名, 获取手机号
		String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
		// 执行发送操作
		smsCodeSender.send(mobile, validateCode.getCode());
	}
}
