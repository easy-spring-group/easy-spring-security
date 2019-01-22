package com.bcdbook.security.core.validate.code.sms;

/**
 * 短信发送器
 *
 * @author summer
 * @date 2019-01-22 11:25
 * @version V1.0.0-RELEASE
 */
public interface SmsCodeSender {

    /**
     * 发送短信验证码
     * TODO 此处需要根据不同的类型选择不同的模板发送, 还需要再优化
     *
     * @author summer
     * @date 2019-01-22 11:27
     * @param mobile 手机号
     * @param code 验证码
     * @return void
     * @version V1.0.0-RELEASE
     */
    void send(String mobile, String code);
}
