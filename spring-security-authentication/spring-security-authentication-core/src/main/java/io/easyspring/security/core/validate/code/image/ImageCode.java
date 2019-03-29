package io.easyspring.security.core.validate.code.image;

import io.easyspring.security.core.validate.code.ValidateCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;

/**
 * 图片验证码
 *
 * @author summer
 * @version V1.0.0-RELEASE
 * DateTime 2019-01-21 18:12
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageCode extends ValidateCode {

    private static final long serialVersionUID = -8707549135300454578L;

    /**
     * 验证码图片
     */
	private BufferedImage image;

    /**
     * 根据有效时长生成的图片验证码对象
     *
     * Author summer
     * DateTime 2019-01-16 23:46
     * @param image 图片对象
     * @param code 验证码
     * @param expire 有效时长
     * Version V1.0.0-RELEASE
     */
    public ImageCode(BufferedImage image, String code, int expire){
        super(code, expire);
        this.image = image;
    }
}
