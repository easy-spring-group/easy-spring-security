package com.bcdbook.security.browser.authentication;

import com.bcdbook.security.core.properties.SecurityProperties;
import com.bcdbook.security.core.properties.SignInResponseType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 浏览器环境下登录失败的处理器
 *
 * @author summer
 * @date 2019-01-21 17:16
 * @version V1.0.0-RELEASE
 */
@Component("easyAuthenticationFailureHandler")
@Slf4j
public class EasyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * 注入 jackson 的 mapper 工具
     */
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 注入 security 的配置
     */
    @Autowired
    private SecurityProperties securityProperties;


    /**
     * 复写登录失败的处理逻辑
     *
     * @author summer
     * @date 2019-01-17 11:58
     * @param request 请求信息
     * @param response 返回信息
     * @param exception 异常
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        log.info("登录失败");

        // 如果设定的是返回 json 信息
        if (SignInResponseType.JSON.equals(securityProperties.getBrowser().getSignInResponseType())) {
            // 设置返回状态码
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            // 设置返回类型和编码
            response.setContentType("application/json;charset=UTF-8");
            // 返回信息
            response.getWriter().write(objectMapper.writeValueAsString(exception.getMessage()));
        }else{
            // 如果不是 json 则直接调用父类的返回(页面跳转)
            super.onAuthenticationFailure(request, response, exception);
        }
    }

}
