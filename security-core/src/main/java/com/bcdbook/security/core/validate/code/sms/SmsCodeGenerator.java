package com.bcdbook.security.core.validate.code.sms;

import com.bcdbook.security.core.properties.SecurityProperties;
import com.bcdbook.security.core.validate.code.ValidateCode;
import com.bcdbook.security.core.validate.code.ValidateCodeGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 默认的图片验证码生成器
 *
 * @author summer
 * @date 2019-01-16 23:48
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    /**
     * 注入 security 的配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 短信验证码的生成器
     *
     * @author summer
     * @date 2019-01-22 11:41
     * @param request Servlet 请求信息
     * @return com.bcdbook.security.core.validate.code.ValidateCode
     * @version V1.0.0-RELEASE
     */
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpire());
    }

}
