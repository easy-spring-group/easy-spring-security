package io.easyspring.security.core.validate.code.sms;

/**
 * 短信发送器
 *
 * @author summer
 * DateTime 2019-01-22 11:25
 * @version V1.0.0-RELEASE
 */
public interface SmsCodeSender {

    /**
     * 发送短信验证码
     *
     * Author summer
     * DateTime 2019-01-22 11:27
     * @param mobile 手机号
     * @param code 验证码
     * @param template 短信模板识别码
     * Version V1.0.0-RELEASE
     */
    void send(String mobile, String code, String template);
}
