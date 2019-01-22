package com.bcdbook.security.core.validate.code;

import com.bcdbook.security.core.properties.SecurityConstants;

/**
 * 校验码类型
 *
 * @author summer
 * @date 2019-01-17 14:49
 * @version V1.0.0-RELEASE
 */
public enum ValidateCodeType {

	/**
	 * 短信验证码
	 */
	SMS {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.PARAMETER_NAME_CODE_SMS;
		}
	},
	/**
	 * 图片验证码
	 */
	IMAGE {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.PARAMETER_NAME_CODE_IMAGE;
		}
	};

	/**
	 * 校验时从请求中获取的参数的名字(名字是约定好的 -- 可配置)
	 *
	 * @author summer
	 * @date 2019-01-17 14:46
	 * @return java.lang.String
	 * @version V1.0.0-RELEASE
	 */
	public abstract String getParamNameOnValidate();

}
