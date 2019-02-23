package io.easyspring.security.browser.logout;

import io.easyspring.security.browser.support.SimpleResponse;
import io.easyspring.security.core.properties.browser.enums.SignOutSuccessResultTypeEnum;
import io.easyspring.security.core.utils.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
     * 退出成功后返回的数据类型
     */
    private SignOutSuccessResultTypeEnum resultType;
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
     * @param resultType 退出成功后返回的数据类型
     * @version V1.0.0-RELEASE
     */
    public EasySignOutSuccessHandler(String signOutSuccessUrl, SignOutSuccessResultTypeEnum resultType) {
        this.signOutSuccessUrl = signOutSuccessUrl;
        this.resultType = resultType;
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

        // 定义当前处理的返回数据类型
        SignOutSuccessResultTypeEnum selfResultType = resultType;

        // 如果配置的返回数据类型为空, 获取返回数据类型为 AUTO
        if (selfResultType == null || selfResultType.equals(SignOutSuccessResultTypeEnum.AUTO)) {
            // 获取请求方式
            boolean isHtml = RequestUtils.isHtml(request);
            // 如果是 html 请求
            if (isHtml) {
                // 设置返回的数据类型为 html
                selfResultType = SignOutSuccessResultTypeEnum.HTML;
            // 如果不是 html 请求, 则设置返回的数据类型为 json
            } else {
                selfResultType = SignOutSuccessResultTypeEnum.JSON;
            }
        }

        // 如果返回的数据类型是 html, 则直接跳转到退出成功的页面
        if (selfResultType.equals(SignOutSuccessResultTypeEnum.HTML)) {
            response.sendRedirect(signOutSuccessUrl);
        // 如果返回数据类型是 json, 则封装 json 并返回
        } else if (selfResultType.equals(SignOutSuccessResultTypeEnum.JSON)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        }
    }

}
