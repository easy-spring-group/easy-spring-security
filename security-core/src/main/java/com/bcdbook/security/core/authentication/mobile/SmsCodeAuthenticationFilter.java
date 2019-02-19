package com.bcdbook.security.core.authentication.mobile;

import com.bcdbook.security.core.properties.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     * 手机号登录时, 传入的手机号的参数名称
     */
    private String mobileParameter = SecurityConstants.Validate.PARAMETER_NAME_MOBILE;
    /**
     * 是否只允许 post 请求
     */
    private boolean postOnly = true;

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
        super(new AntPathRequestMatcher(SecurityConstants.SignIn.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                RequestMethod.POST.name()));
    }

    /**
     * 重写尝试登录的方法
     *
     * @author summer
     * @date 2019-01-22 12:47
     * @param request 请求信息
     * @param response 返回信息
     * @return org.springframework.security.core.Authentication
     * @version V1.0.0-RELEASE
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // 如果请求类型不是 post 则抛出异常
        if (!request.getMethod().equals(RequestMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // 获取前台传入的手机号
        String mobile = obtainMobile(request);
        if (mobile == null) {
            mobile = "";
        }

        mobile = mobile.trim();

        // 构建认证的缓存信息
        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

        // 允许子类设置 “details” 属性
        setDetails(request, authRequest);

        // 获取权限校验控制器并执行权限校验
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 获取手机号
     *
     * @author summer
     * @date 2019-01-22 16:03
     * @param request 请求信息
     * @return java.lang.String
     * @version V1.0.0-RELEASE
     */
    protected String obtainMobile(HttpServletRequest request) {
        // 根据参数名, 从 request 请求中获取手机号
        return request.getParameter(mobileParameter);
    }

    /**
     * 设置身份验证后 detail 信息的方法
     * 以便子类可以配置放入身份验证请求的 details 属性中的内容。
     *
     * @author summer
     * @date 2019-01-22 16:17
     * @param request 请求信息
     * @param authRequest 验证后信息的对象
     * @return void
     * @version V1.0.0-RELEASE
     */
    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from
     * the login request.
     *
     * @param usernameParameter
     *            the parameter name. Defaults to "username".
     */
    public void setMobileParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.mobileParameter = usernameParameter;
    }


    /**
     * Defines whether only HTTP POST requests will be allowed by this filter.
     * If set to true, and an authentication request is received which is not a
     * POST request, an exception will be raised immediately and authentication
     * will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
     * will be called as if handling a failed authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }
}
