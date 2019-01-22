package com.bcdbook.security.core.authentication.mobile;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 手机验证码权限校验的过滤器
 * 继承抽象的权限校验的拦截器, 并实现相应的校验方法
 *
 * @author summer
 * @date 2019-01-22 12:43
 * @version V1.0.0-RELEASE
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 空参构造
     * 设定拦截的接口和请求方式
     * 当设定好拦截的接口和请求方式以后, 客户端向此接口发送请求的时候, 就会被此过滤器拦截到
     *
     * @author summer
     * @date 2019-01-22 12:45
     * @version V1.0.0-RELEASE
     */
    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/authentication/mobile", "POST"));
    }

    /**
     * 重写尝试登录的方法
     *
     * @author summer
     * @date 2019-01-22 12:47
     * @param request 请求信息
     * @param response 返回新
     * @return org.springframework.security.core.Authentication
     * @version V1.0.0-RELEASE
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        // 如果不是 post 请求, 说明拦截器出错了
        if(!StringUtils.equalsIgnoreCase("post", request.getMethod())){
            throw new AuthenticationServiceException("登录请求只支持POST方法");
        }

        // 从请求中获取手机号
        String mobile = ServletRequestUtils.getStringParameter(request, "mobile");
        // 对获取的手机号进行校验
        if(StringUtils.isBlank(mobile)){
            throw new AuthenticationServiceException("手机号不能为空");
        }

        // 构建一个存储器, 用于存储需要登录时的手机号
        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
        // 把请求信息设置到 token 中
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

        // 获取登录的控制器, 并执行登录操作
        return getAuthenticationManager().authenticate(authRequest);
    }

}
