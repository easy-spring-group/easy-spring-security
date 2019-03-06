package io.easyspring.security.core.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认的短信发送器
 * 用于用户覆盖
 *
 * @author summer
 * @date 2019-01-22 11:26
 * @version V1.0.0-RELEASE
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

    /**
     * 发送短信验证码
     *
     * @author summer
     * @date 2019-01-22 11:27
     * @param mobile 手机号
     * @param code 验证码
     * @param template 短信模板识别码
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void send(String mobile, String code, String template) {
        log.info("向手机: {}, 发送短信验证码: {}, 使用的模板是: {}", mobile, code, template);
    }

}
