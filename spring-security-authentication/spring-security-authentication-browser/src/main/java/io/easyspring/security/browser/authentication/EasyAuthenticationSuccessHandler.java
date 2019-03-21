package io.easyspring.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.easyspring.security.core.properties.SecurityProperties;
import io.easyspring.security.core.properties.SignInResponseType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 浏览器环境下登录成功的处理器
 *
 * @author summer
 * DateTime 2019-01-17 12:02
 * @version V1.0.0-RELEASE
 */
@Component("easyAuthenticationSuccessHandler")
@Slf4j
public class EasyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * 注入 jackson 的 mapper 工具
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 注入 security 的配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 创建请求的缓存器
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * 登录成功后的处理逻辑
     *
     * Author summer
     * DateTime 2019-01-17 12:05
     * @param request 请求信息
     * @param response 返回信息
     * @param authentication 登录的用户信息
     * Version V1.0.0-RELEASE
     */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

		log.info("登录成功");

		// 如果设定返回的信息类型是 json
		if (SignInResponseType.JSON.equals(securityProperties.getBrowser().getSignInResponseType())) {
		    // 设置返回类型及编码
			response.setContentType("application/json;charset=UTF-8");
			// 发送到前端
			response.getWriter().write(objectMapper.writeValueAsString(authentication));
		} else {
			// 如果设置了 easy-spring.security.browser.sing-in-success-url ，总是跳到设置的地址上
			// 如果没设置，则尝试跳转到登录之前访问的地址上，如果登录前访问地址为空，则跳到网站根路径上
			if (StringUtils.isNotBlank(securityProperties.getBrowser().getSingInSuccessUrl())) {
			    // 删除缓存中的请求信
				requestCache.removeRequest(request, response);
				// 设置使用默认的地址
				setAlwaysUseDefaultTargetUrl(true);
				// 设置登录成功后的地址
				setDefaultTargetUrl(securityProperties.getBrowser().getSingInSuccessUrl());
			}

			// 如果没有设置默认的登录成功后的跳转地址, 则直接使用父类的跳转方法
			super.onAuthenticationSuccess(request, response, authentication);
		}

	}

}
