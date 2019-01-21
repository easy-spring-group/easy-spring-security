package com.bcdbook.security.core.properties.code;

import lombok.Data;

/**
 * 图片验证码配置项
 *
 * @author summer
 * @date 2019-01-16 20:53
 * @version V1.0.0-RELEASE
 */
@Data
public class ImageCodeProperties {

	/**
	 * 图片宽
	 */
	private int width = 67;
	/**
	 * 图片高
	 */
	private int height = 23;
    /**
     * 验证码长度
     */
    private int length = 4;
    /**
     * 有效时长 (单位是: 秒)
     */
    private int expire = 60;
    /**
     * 需要拦截的地址
     */
    private String urls;

}
