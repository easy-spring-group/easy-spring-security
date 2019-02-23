package io.easyspring.security.social;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 安全校验的配置类
 *
 * @author summer
 * @date 2019-01-24 16:39
 * @version V1.0.0-RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EasySpringSocialConfigurer extends SpringSocialConfigurer {

    /**
     * 社交登录时过滤的地址
     */
    private String filterProcessesUrl;
    /**
     * 社交登录的后处理器
     */
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    /**
     * 全参构造方法
     *
     * @author summer
     * @date 2019-01-24 16:40
     * @param filterProcessesUrl 社交登录时过滤的地址
     * @version V1.0.0-RELEASE
     */
    public EasySpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    /**
     * 重写父级的过滤器拦截地址配置, 改成自己的过滤器地址
     *
     * @author summer
     * @date 2019-01-24 16:43
     * @param object 要放到过滤器链上的 filter
     * @return T
     * @version V1.0.0-RELEASE
     */
    @SuppressWarnings("unchecked")
    @Override
    protected <T> T postProcess(T object) {
        // 调用父级的方法, 生成 social 权限的过滤器
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        // 设置成自己的过滤地址
        filter.setFilterProcessesUrl(filterProcessesUrl);
        // 设置社交登录的后处理器
        if (socialAuthenticationFilterPostProcessor != null) {
            socialAuthenticationFilterPostProcessor.process(filter);
        }

        return (T) filter;
    }

}
