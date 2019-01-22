package com.bcdbook.security.core.validate.code;

import com.bcdbook.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
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
    public static final String SESSION_KEY_FOR_CODE_IMAGE = "SESSION_KEY_IMAGE_CODE";
    /**
     * 定义短信验证码存储在 Session 中的 key
     */
    public static final String SESSION_KEY_FOR_CODE_SMS = "SESSION_KEY_SMS_CODE";


    /**
     * 用于操作 session 的工具类
     */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 注入图片验证码的生成器
     */
	@Autowired
    private ValidateCodeGenerator imageCodeGenerator;
    /**
     * 注入短信验证码的生成器
     */
    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;
    /**
     * 注入短信验证码的发送器
     */
    @Autowired
    private SmsCodeSender smsCodeSender;


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
		ImageCode imageCode = (ImageCode)imageCodeGenerator.generate(new ServletWebRequest(request));

        /*
		 * 把生成的图片验证码设置到 session 中
		 * arg1: 请求信息, sessionStrategy 会从这里拿到 session
		 * arg2: 保存时使用的 key
		 * arg3: 生成的图片验证码对象
		 */
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_FOR_CODE_IMAGE, imageCode);
		// 用流写出去
		ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
		
	}

    /**
     * 获取短信验证码的接口
     *
     * @author summer
     * @date 2019-01-22 11:31
     * @param request 请求信息
     * @param response 返回信息
     * @return void
     * @version V1.0.0-RELEASE
     */
    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_FOR_CODE_SMS, smsCode);
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
        smsCodeSender.send(mobile, smsCode.getCode());
    }


}
