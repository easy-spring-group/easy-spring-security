package io.easyspring.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 校验码处理器管理器
 *
 * @author summer
 * DateTime 2019-01-17 15:08
 * @version V1.0.0-RELEASE
 */
@Component
public class ValidateCodeProcessorHolder {

    /**
     * 注入验证码处理器的集合
     */
	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * 根据验证码枚举类型获取验证码
     *
     * Author summer
     * DateTime 2019-01-17 15:10
     * @param type 验证码类型枚举
     * @return io.easyspring.security.code.ValidateCodeProcessor
     * Version V1.0.0-RELEASE
     */
	public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
		return findValidateCodeProcessor(type.toString().toLowerCase());
	}

    /**
     * 根据验证码类型字符串获取验证码
     *
     * Author summer
     * DateTime 2019-01-17 15:11
     * @param type 验证码类型
     * @return io.easyspring.security.code.ValidateCodeProcessor
     * Version V1.0.0-RELEASE
     */
	public ValidateCodeProcessor findValidateCodeProcessor(String type) {
	    // 拼装出验证码处理器的名字
		String validateCodeProcessorName = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
		// 从已经注册的验证码处理器集合中获取验证码处理器
		ValidateCodeProcessor processor = validateCodeProcessors.get(validateCodeProcessorName);

		// 如果没有获取到, 则抛出异常
		if (processor == null) {
			throw new ValidateCodeException("验证码处理器" + validateCodeProcessorName + "不存在");
		}
		// 返回找到的验证码处理器
		return processor;
	}

}
