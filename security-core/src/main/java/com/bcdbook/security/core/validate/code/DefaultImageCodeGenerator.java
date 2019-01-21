package com.bcdbook.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 默认的图片验证码生成器
 *
 * @author summer
 * @date 2019-01-16 23:48
 * @version V1.0.0-RELEASE
 */
public class DefaultImageCodeGenerator implements ValidateCodeGenerator {

    /**
     * RGB 颜色的最大值
     */
    private static final int MAX_RGB_VALUE = 255;

    /**
     * 生成方法
     *
     * @author summer
     * @date 2019-01-16 23:49
     * @param request 请求对象
     * @return com.bcdbook.security.core.validate.code.image.ImageCode
     * @version V1.0.0-RELEASE
     */
	@Override
	public ImageCode generate(ServletWebRequest request) {
		int width = 67;
		int height = 23;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();

		Random random = new Random();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 16);
		}

		g.dispose();

		return new ImageCode(image, sRand, 60);
	}
	
    /**
     * 生成随机背景条纹
     *
     * @author summer
     * @date 2019-01-21 18:10
     * @param fc 前景色
     * @param bc 后景色
     * @return java.awt.Color
     * @version V1.0.0-RELEASE
     */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > MAX_RGB_VALUE) {
			fc = MAX_RGB_VALUE;
		}
		if (bc > MAX_RGB_VALUE) {
			bc = MAX_RGB_VALUE;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
