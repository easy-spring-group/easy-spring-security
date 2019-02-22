package com.bcdbook.security.app.authentication.openid;

import com.bcdbook.security.core.properties.SecurityConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 社交登录认证的过滤器
 *
 * @author summer
 * @date 2019-02-21 09:57
 * @version V1.0.0-RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * openId 的参数名
     */
    private String openIdParameter = SecurityConstants.Social.DEFAULT_PARAMETER_NAME_OPENID_NAME;
    /**
     * providerId 的参数名
     */
    private String providerIdParameter = SecurityConstants.Social.DEFAULT_PARAMETER_NAME_PROVIDER_ID_NAME;
    /**
     * 是否只拦截 post 请求
     */
    private boolean postOnly = true;

    /**
     * 空参构造
     *
     * @author summer
     * @date 2019-02-21 09:59
     * @version V1.0.0-RELEASE
     */
    public OpenIdAuthenticationFilter() {
        // 通过父级方法, 设置请求地址匹配器, 同时设置请求方法
        super(new AntPathRequestMatcher(SecurityConstants.Social.DEFAULT_LOGIN_PROCESSING_URL_OPENID,
                HttpMethod.POST.name()));
    }



    /**
     * 重写预授权的方法
     *
     * @author summer
     * @date 2019-02-21 10:01
     * @param request 请求信息
     * @param response 返回信息
     * @return org.springframework.security.core.Authentication
     * @version V1.0.0-RELEASE
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // 如果请求类型不是 post, 但是进入预授权, 则直接抛出异常
        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        /*
         * 获取请求信息处理
         */
        // 获取 openId
        String openid = obtainOpenId(request);
        // 获取 providerId
        String providerId = obtainProviderId(request);

        // 非空校验
        if (openid == null) {
            openid = "";
        }
        if (providerId == null) {
            providerId = "";
        }

        // 去除空格
        openid = openid.trim();
        providerId = providerId.trim();

        /*
         * 构建认证信息
         */
        OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openid, providerId);

        // 设置认证详细信息
        setDetails(request, authRequest);

        // 获取认证器, 并执行认证
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     * 从请求中获取 openId
     *
     * @author summer
     * @date 2019-02-21 10:02
     * @param request 请求信息
     * @return java.lang.String
     * @version V1.0.0-RELEASE
     */
    protected String obtainOpenId(HttpServletRequest request) {
        return request.getParameter(openIdParameter);
    }


    /**
     * 从请求中获取提供商 id
     *
     * @author summer
     * @date 2019-02-21 10:03
     * @param request 请求信息
     * @return java.lang.String
     * @version V1.0.0-RELEASE
     */
    protected String obtainProviderId(HttpServletRequest request) {
        return request.getParameter(providerIdParameter);
    }

    /**
     * 设置认证的详细属性信息
     *
     * @author summer
     * @date 2019-02-21 10:07
     * @param request 请求信息
     * @param authRequest 认证信息
     * @return void
     * @version V1.0.0-RELEASE
     */
    protected void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
