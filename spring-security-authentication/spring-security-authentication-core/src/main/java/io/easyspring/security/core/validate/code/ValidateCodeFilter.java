package io.easyspring.security.core.validate.code;

import io.easyspring.security.core.properties.SecurityConstants;
import io.easyspring.security.core.properties.SecurityProperties;
import io.easyspring.security.core.properties.code.ValidateCodeFilterUrlProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 验证码的过滤器
 *
 * @author summer
 * DateTime 2019-01-17 15:37
 * @version V1.0.0-RELEASE
 */
@Component("validateCodeFilter")
@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 注入权限校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 存放所有需要校验验证码的 url
     */
    private List<ValidateCodeFilterUrlProperties> filterUrlList = new ArrayList<>();
    /**
     * 验证请求 url 与配置的 url 是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 初始化要拦截的 url 配置信息
     * 在父级的 bean 初始化完成后, 进行设置的动作
     *
     * Author summer
     * DateTime 2019-01-17 15:41
     * Version V1.0.0-RELEASE
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        // 调用父级的初始化方法
        super.afterPropertiesSet();

        /*
         * 设置需要拦截的接口(需要验证码, 可能是图片验证码也可能是短信验证码)
         */
        List<ValidateCodeFilterUrlProperties> validateCodeFilterUrlPropertiesList =
                securityProperties.getCode().getFilterUrls();
        if (!CollectionUtils.isEmpty(validateCodeFilterUrlPropertiesList)) {
            filterUrlList.addAll(validateCodeFilterUrlPropertiesList);
        }

        // 创建短信验证码的登录请求, 并设置到拦截器中
        ValidateCodeFilterUrlProperties smsSigninUrl = new ValidateCodeFilterUrlProperties(
                SecurityConstants.SignIn.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                RequestMethod.POST,
                ValidateCodeType.SMS);
        filterUrlList.add(smsSigninUrl);
    }

    /**
     * 把自己的验证码过滤器加入的其中
     * 配合 {@link ValidateCodeSecurityConfig} 中的 configure() 使用)
     * 把此处注入的过滤器加入到过滤器链的对应位置
     *
     * 重写 {@link org.springframework.web.filter.OncePerRequestFilter} 的过滤器,
     *
     * Author summer
     * DateTime 2019-01-17 15:51
     * @param request 请求信息
     * @param response 返回信息
     * @param chain 拦截器链
     * Version V1.0.0-RELEASE
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // 获取验证码的类型
        ValidateCodeType type = getValidateCodeType(request);

        // 如果验证码类型为空, 则直接跳过
        if (type == null) {
            chain.doFilter(request, response);
            return;
        }

        // 如果验证码的类型不为空
        // 输入日志
        log.info("校验请求: {}, 验证码类型: {}", request.getRequestURI(), type);

        try {
            // 获取验证码的处理器并执行验证码的验证
            validateCodeProcessorHolder.findValidateCodeProcessor(type)
                    .validate(buildServletWebRequest(request, response));

            log.info("验证码校验通过");
        } catch (ValidateCodeException exception) {
            // 通过权限校验处理器发送出校验失败的信息
            authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
            return;
        }

        // 如果校验通过, 则继续执行接下来的请求
        chain.doFilter(request, response);

    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回 null
     *
     * Author summer
     * DateTime 2019-01-17 16:01
     * @param request 请求信息
     * @return io.easyspring.security.code.ValidateCodeType
     * Version V1.0.0-RELEASE
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        // 定义需要校验的类型
        ValidateCodeType validateCodeType = null;

        boolean urlMatch = false;
        boolean methodMatch = false;
        for (ValidateCodeFilterUrlProperties filterUrlProperties : filterUrlList) {
            if (filterUrlProperties != null) {
                urlMatch = pathMatcher.match(filterUrlProperties.getUrl(), request.getRequestURI());
                methodMatch = request.getMethod().equalsIgnoreCase(filterUrlProperties.getMethod().name());
                if (urlMatch && methodMatch) {
                    validateCodeType = filterUrlProperties.getValidateCodeType();
                }
            }

            urlMatch = false;
            methodMatch = false;
        }

        // 返回需要校验的验证码类型
        return validateCodeType;
    }

    /**
     * 封装 ServletWebRequest 的方法
     *
     * @param request 请求对象
     * @param response 返回对象
     * @return org.springframework.web.context.request.ServletWebRequest
     * Author summer
     * DateTime 2019-03-07 18:43
     * Version V1.0.0-RELEASE
     */
    private ServletWebRequest buildServletWebRequest(HttpServletRequest request, HttpServletResponse response){
        // 参数校验
        if (request == null || response == null) {
            return null;
        }

        // 从请求头中获取 deviceId
        String deviceId = request.getHeader(SecurityConstants.Validate.DEFAULT_HEADER_DEVICE_ID_KEY);

        /*
         * 创建并封装 ServletWebRequest 对象
         */
        // 创建 ServletWebRequest 对象
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
        // 设置 deviceId 到 ServletWebRequest 中
        servletWebRequest.setAttribute(SecurityConstants.Validate.DEFAULT_HEADER_DEVICE_ID_KEY,
                deviceId,
                SecurityConstants.Validate.DEFAULT_DEVICE_ID_EXPIRE);

        return servletWebRequest;
    }
}
