package com.bcdbook.security.core.validate.code.sms;

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
     * TODO 此处需要根据不同的类型选择不同的模板发送, 还需要再优化
     *
     * @author summer
     * @date 2019-01-22 11:27
     * @param mobile 手机号
     * @param code 验证码
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void send(String mobile, String code) {
        log.info("向手机: {}, 发送短信验证码: {}", mobile, code);
    }

}
