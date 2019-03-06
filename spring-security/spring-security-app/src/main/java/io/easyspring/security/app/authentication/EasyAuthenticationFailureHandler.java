package io.easyspring.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
 * APP 环境下登录失败的处理器
 *
 * @author summer
 * @date 2019-02-19 17:18
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
     * 复写登录失败的处理逻辑
     *
     * @author summer
     * @date 2019-02-19 17:19
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

        // 设置返回状态码
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // 设置返回类型和编码
        response.setContentType("application/json;charset=UTF-8");
        // 返回信息
        response.getWriter().write(objectMapper.writeValueAsString(exception.getMessage()));
    }

}