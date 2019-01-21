package com.bcdbook.security.core.validate.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图片验证码
 *
 * @author summer
 * @date 2019-01-21 18:12
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageCode {

    /**
     * 验证码图片
     */
	private BufferedImage image;
    /**
     * 验证码的值
     */
	private String code;
    /**
     * 过期时间(有效时间至XXX)
     */
	private LocalDateTime expireTime;

    /**
     * 校验验证码是否已经过期
     *
     * @author summer
     * @date 2019-01-17 14:52
     * @return boolean
     * @version V1.0.0-RELEASE
     */
	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expireTime);
	}

    /**
     * 根据有效时长生成的图片验证码对象
     *
     * @author summer
     * @date 2019-01-16 23:46
     * @param image 图片对象
     * @param code 验证码
     * @param expire 有效时长
     * @version V1.0.0-RELEASE
     */
    public ImageCode(BufferedImage image, String code, int expire){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expire);
        this.image = image;
    }
}
