package io.easyspring.security.core.validate.code.sms;

import io.easyspring.security.core.validate.code.ValidateCode;
import io.easyspring.security.core.properties.SecurityProperties;
import io.easyspring.security.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 默认的图片验证码生成器
 *
 * @author summer
 * @version V1.0.0-RELEASE
 * DateTime 2019-01-16 23:48
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    /**
     * 注入 security 的配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 短信验证码的生成器
     *
     * Author summer
     * DateTime 2019-01-22 11:41
     * @param request Servlet 请求信息
     * @return io.easyspring.security.code.ValidateCode
     * Version V1.0.0-RELEASE
     */
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpire());
    }

}
