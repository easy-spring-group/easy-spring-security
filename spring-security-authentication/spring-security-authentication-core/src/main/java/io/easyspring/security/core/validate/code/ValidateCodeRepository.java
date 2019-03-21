package io.easyspring.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码存取器接口
 * 具体使用时, 由使用者去实现
 *
 * @author summer
 * DateTime 2019-01-17 14:10
 * @version V1.0.0-RELEASE
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码的方法
     *
     * Author summer
     * DateTime 2019-01-17 14:14
     * @param request Servlet 请求信息
     * @param code 抽象的验证码对象
     * @param validateCodeType 验证码类型
     * Version V1.0.0-RELEASE
     */
	void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);

    /**
     * 获取验证码的方法
     *
     * Author summer
     * DateTime 2019-01-17 14:15
     * @param request Servlet 请求对象
     * @param validateCodeType 验证码类型
     * @return io.easyspring.security.code.ValidateCode
     * Version V1.0.0-RELEASE
     */
	ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);

    /**
     * 删除缓存中验证码的方法
     *
     * Author summer
     * DateTime 2019-01-17 14:33
     * @param request Servlet 请求对象
     * @param codeType 验证码类型
     * Version V1.0.0-RELEASE
     */
	void remove(ServletWebRequest request, ValidateCodeType codeType);

}
