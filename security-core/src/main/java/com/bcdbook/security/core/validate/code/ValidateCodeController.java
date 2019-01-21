package com.bcdbook.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码生成器的控制器
 *
 * @author summer
 * @date 2019-01-21 18:17
 * @version V1.0.0-RELEASE
 */
@RestController
public class ValidateCodeController {

    /**
     * 定义存储图片验证码在 session 中时的 key
     */
	public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    /**
     * 用于操作 session 的工具类
     */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 注入验证码的生成器
     */
	@Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    /**
     * 生成图片验证的接口
     *
     * @author summer
     * @date 2019-01-21 18:20
     * @param request 请求对象
     * @param response 返回对象
     * @return void
     * @version V1.0.0-RELEASE
     */
	@GetMapping("/code/image")
	public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

	    // 生成图片验证码
		ImageCode imageCode = imageCodeGenerator.generate(new ServletWebRequest(request));
		/*
		 * 把生成的图片验证码设置到 session 中
		 * arg1: 请求信息, sessionStrategy 会从这里拿到 session
		 * arg2: 保存时使用的 key
		 * arg3: 生成的图片验证码对象
		 */
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
		// 用流写出去
		ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
		
	}

}
