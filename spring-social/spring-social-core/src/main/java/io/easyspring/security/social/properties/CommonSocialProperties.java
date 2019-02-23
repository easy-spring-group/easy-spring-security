package io.easyspring.security.social.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * social 基础的配置文件内容
 *
 * @author summer
 * @date 2019-01-24 15:00
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonSocialProperties implements Serializable {

    private static final long serialVersionUID = 7685046511597307907L;

    /**
     * 项目 id
     */
    private String appId;
    /**
     * 项目密码
     */
    private String appSecret;
}
