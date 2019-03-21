package io.easyspring.security.core.validate.code;

import io.easyspring.security.core.properties.SecurityConstants;

/**
 * 校验码类型
 *
 * @author summer
 * DateTime 2019-01-17 14:49
 * @version V1.0.0-RELEASE
 */
public enum ValidateCodeType {

	/**
	 * 短信验证码
	 */
	SMS {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.Validate.PARAMETER_NAME_CODE_SMS;
		}
	},
	/**
	 * 图片验证码
	 */
	IMAGE {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.Validate.PARAMETER_NAME_CODE_IMAGE;
		}
	};

	/**
	 * 校验时从请求中获取的参数的名字(名字是约定好的 -- 可配置)
	 *
	 * Author summer
	 * DateTime 2019-01-17 14:46
	 * @return java.lang.String
	 * Version V1.0.0-RELEASE
	 */
	public abstract String getParamNameOnValidate();

}
