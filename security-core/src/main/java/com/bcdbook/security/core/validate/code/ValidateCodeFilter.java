package com.bcdbook.security.core.validate.code;

import com.bcdbook.security.core.properties.SecurityConstants;
import com.bcdbook.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 验证码的过滤器
 *
 * @author summer
 * @date 2019-01-17 15:37
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
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();
    /**
     * 验证请求 url 与配置的 url 是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 初始化要拦截的 url 配置信息
     * 在父级的 bean 初始化完成后, 进行设置的动心
     *
     * @author summer
     * @date 2019-01-17 15:41
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        // 调用父级的初始化方法
        super.afterPropertiesSet();

        /*
         * 设置需要突破验证码的接口
         */
        // 设置 form 登录接口需要突破验证码
        //urlMap.put(SecurityConstants.SIGN_IN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        // 循环设置需要图片验证码的地址
        addUrlToMap(securityProperties.getCode().getImage().getUrls(), ValidateCodeType.IMAGE);

        // 设置手机验证码登录的请求需要短信验证
        urlMap.put(SecurityConstants.SIGN_IN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        // 循环设置需要短信验证码的地址
        addUrlToMap(securityProperties.getCode().getSms().getUrls(), ValidateCodeType.SMS);
    }

    /**
     * 将系统中配置的需要校验验证码的 URL 根据校验的类型放入 map
     *
     * @author summer
     * @date 2019-01-17 15:45
     * @param urlString 需要验证码校验的 url (以逗号隔开的字符串)
     * @param type 需要的验证类型
     * @return void
     * @version V1.0.0-RELEASE
     */
    protected void addUrlToMap(String urlString, ValidateCodeType type) {
        // 如果传入的需要拦截的地址不为空则进入循环添加
        if (StringUtils.isNotBlank(urlString)) {
            // 以逗号为分隔符, 分割出需要拦截的地址
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }

    /**
     * 把自己的验证码过滤器加入的其中
     * 配合 {@link ValidateCodeSecurityConfig} 中的 configure() 使用)
     * 把此处注入的过滤器加入到过滤器链的对应位置
     *
     * 重写 {@link org.springframework.web.filter.OncePerRequestFilter} 的过滤器,
     *
     * @author summer
     * @date 2019-01-17 15:51
     * @param request 请求信息
     * @param response 返回信息
     * @param chain 拦截器链
     * @return void
     * @version V1.0.0-RELEASE
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
        log.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);

        try {
            // 获取验证码的处理器并执行验证码的验证
            validateCodeProcessorHolder.findValidateCodeProcessor(type)
                    .validate(new ServletWebRequest(request, response));

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
     * @author summer
     * @date 2019-01-17 16:01
     * @param request 请求信息
     * @return com.bcdbook.security.code.ValidateCodeType
     * @version V1.0.0-RELEASE
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        // 定义需要校验的类型
        ValidateCodeType result = null;

        // 忽略大小写匹配, 如果请求的方法是如果不是 get 请求
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), RequestMethod.GET.name())) {
            // 获取需要拦截的请求的 key (也就是需要拦截的地址)
            Set<String> urls = urlMap.keySet();
            // 循环匹配
            // TODO: 2019-01-21 此处只能拦截请求地址, 而不能区分请求方式, 需要优化
            for (String url : urls) {
                // 如果当前的地址和需要验证码校验的地址匹配(使用 pathMatcher 匹配器匹配)
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }

        // 返回需要校验的验证码类型
        return result;
    }
}
