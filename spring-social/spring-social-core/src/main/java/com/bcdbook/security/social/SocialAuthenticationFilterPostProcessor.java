package com.bcdbook.security.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * 社交登录的后处理器 接口
 *
 * @author summer
 * @date 2019-02-20 20:02
 * @version V1.0.0-RELEASE
 */
public interface SocialAuthenticationFilterPostProcessor {

    /**
     * 社交登录的后处理器, 对社交登录过滤器修改的操作
     *
     * @author summer
     * @date 2019-02-20 20:05
     * @param socialAuthenticationFilter 社交登录的过滤器
     * @return void
     * @version V1.0.0-RELEASE
     */
    void process(SocialAuthenticationFilter socialAuthenticationFilter);

}
