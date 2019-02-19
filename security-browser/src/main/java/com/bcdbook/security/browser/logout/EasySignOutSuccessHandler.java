package com.bcdbook.security.browser.logout;

import com.bcdbook.security.browser.support.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录成功后的处理器
 *
 * @author summer
 * @date 2019-02-19 13:10
 * @version V1.0.0-RELEASE
 */
@Slf4j
public class EasySignOutSuccessHandler implements LogoutSuccessHandler {


    /**
     * 退出登录成功后的跳转地址
     */
    private String signOutSuccessUrl;

    /**
     * Json 处理的工具类
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 退出登录成功后的处理器 构造方法
     *
     * @author summer
     * @date 2019-02-19 13:10
     * @param signOutSuccessUrl 退出登录成功后的跳转地址
     * @version V1.0.0-RELEASE
     */
    public EasySignOutSuccessHandler(String signOutSuccessUrl) {
        this.signOutSuccessUrl = signOutSuccessUrl;
    }

    /**
     * 退出登录成功后的处理方法
     * @see org.springframework.security.web.authentication.logout.LogoutSuccessHandler#onLogoutSuccess(
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     *
     * @author summer
     * @date 2019-02-19 13:11
     * @param request 请求对象
     * @param response 返回对象
     * @param authentication 权限信息
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {

        log.info("退出成功");

        // 如果退出登录成功后的跳转地址为空, 则直接封装 json 信息返回
        if (StringUtils.isBlank(signOutSuccessUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        } else {
            // 如果配置了地址, 则跳转到配置的地址
            response.sendRedirect(signOutSuccessUrl);
        }
    }

}
