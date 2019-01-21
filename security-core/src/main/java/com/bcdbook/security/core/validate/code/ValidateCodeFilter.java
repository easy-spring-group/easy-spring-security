package com.bcdbook.security.core.validate.code;

import com.bcdbook.security.core.properties.SecurityProperties;
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
 * 验证码的过滤器
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
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 权限校验失败的处理器
     */
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * session 操作的工具
     */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 需要拦截的地址
     */
    private Set<String> urls = new HashSet<>();

    /**
     * 安全配置
     */
    private SecurityProperties securityProperties;
    /**
     * 请求路径匹配器
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 配置文件加载完成后执行的方法
     *
     * @author summer
     * @date 2019-01-21 19:26
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        // 获取配置中需要拦截的 url
        String propertiesUrls = securityProperties.getCode().getImage().getUrls();
        // 把配置中的 url 以逗号进行拆分, 拆分成字符串数组
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(propertiesUrls, ",");
        if (ArrayUtils.isNotEmpty(configUrls)) {
            // 循环配置的需要拦截的地址
            for (String configUrl : configUrls) {
                // 加入需要拦截的地址中
                urls.add(configUrl);
            }
        }
        urls.add("/authentication/form");
    }

    /**
     * 过滤器执行时的过滤方法
     *
     * @author summer
     * @date 2019-01-21 18:41
     * @param request 请求信息
     * @param response 返回信息
     * @param filterChain 过滤器链对象
     * @return void
     * @version V1.0.0-RELEASE
     */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

	    // 定义是否需要校验的返回结果
        boolean action = false;
        // 循环定义好的校验地址
        // TODO: 2019-01-21 此处只能拦截请求地址, 而不能区分请求方式, 需要优化
        for (String url : urls) {
            // 如果需要校验的地址和当前的地址一致
            if(pathMatcher.match(url, request.getRequestURI())){
                // 则说明需要校验
                action = true;
            }
        }

	    // 如果是登录请求, 并且请求方式是 post 的时候
		if(action) {
			
			try {
			    // 执行验证码的校验
				validate(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
			    // 如果出现了异常, 使用登录错误的处理器抛出异常
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
			
		}

		// 通过过滤器链执行接下来的操作
		filterChain.doFilter(request, response);
		
	}

    /**
     * 验证码的校验方法
     *
     * @author summer
     * @date 2019-01-21 18:42
     * @param request 请求对象
     * @return void
     * @version V1.0.0-RELEASE
     */
	private void validate(ServletWebRequest request) throws ServletRequestBindingException {

	    // 获取 session 中的校验码
		ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request,
				ValidateCodeController.SESSION_KEY);

		// 从请求中获取传入的验证码
		String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("验证码的值不能为空");
		}
		
		if(codeInSession == null){
			throw new ValidateCodeException("验证码不存在");
		}

		if(codeInSession.isExpired()){
			sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
			throw new ValidateCodeException("验证码已过期");
		}
		
		if(!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不匹配");
		}

		// 清除缓存中的验证码
		sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
	}

}
