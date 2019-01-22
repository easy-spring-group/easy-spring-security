package com.bcdbook.security.core.validate.code.sms;

import com.bcdbook.security.core.properties.SecurityProperties;
import com.bcdbook.security.core.validate.code.ValidateCode;
import com.bcdbook.security.core.validate.code.ValidateCodeController;
import com.bcdbook.security.core.validate.code.ValidateCodeException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 短信验证码的过滤器
 * OncePerRequestFilter 是 spring 提供的过滤器类, 用于保证我们的过滤器每次请求只会被调用一次
 * InitializingBean
 *
 * @author summer
 * @date 2019-01-21 18:29
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private Set<String> urls = new HashSet<>();

    private SecurityProperties securityProperties;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(
                securityProperties.getCode().getImage().getUrls(), ",");
        if(ArrayUtils.isNotEmpty(configUrls)){
            for (String configUrl : configUrls) {
                urls.add(configUrl);
            }
        }
        urls.add("/authentication/mobile");
    }

    /* (non-Javadoc)
     * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        boolean action = false;
        for (String url : urls) {
            if(pathMatcher.match(url, request.getRequestURI())){
                action = true;
            }
        }

        if(action) {

            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }

        }

        filterChain.doFilter(request, response);

    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {

        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request,
                ValidateCodeController.SESSION_KEY_FOR_CODE_SMS);

        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if(codeInSession == null){
            throw new ValidateCodeException("验证码不存在");
        }

        if(codeInSession.isExpired()){
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY_FOR_CODE_SMS);
            throw new ValidateCodeException("验证码已过期");
        }

        if(!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY_FOR_CODE_SMS);
    }
}
