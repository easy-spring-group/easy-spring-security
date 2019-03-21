package io.easyspring.security.core.validate.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码的基类
 *
 * @author summer
 * DateTime 2019-01-22 11:12
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ValidateCode implements Serializable {

    private static final long serialVersionUID = 3000761826038431060L;

    /**
     * 验证码
     */
    private String code;
    /**
     * 过期时间(有效期至)
     */
    private LocalDateTime expireTime;

    /**
     * 根据有效时长的构造方法
     *
     * Author summer
     * DateTime 2019-01-22 11:13
     * @param code 验证码
     * @param expire 有效时长
     * Version V1.0.0-RELEASE
     */
    public ValidateCode(String code, int expire){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expire);
    }

    /**
     * 校验验证码是否已经过期
     *
     * Author summer
     * DateTime 2019-01-17 14:52
     * @return boolean
     * Version V1.0.0-RELEASE
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }

}
