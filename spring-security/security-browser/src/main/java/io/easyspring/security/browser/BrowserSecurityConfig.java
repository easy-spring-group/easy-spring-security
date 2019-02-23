package io.easyspring.security.browser;

import io.easyspring.security.core.authentication.AbstractChannelSecurityConfig;
import io.easyspring.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import io.easyspring.security.core.properties.SecurityConstants;
import io.easyspring.security.core.properties.SecurityProperties;
import io.easyspring.security.core.validate.code.ValidateCodeSecurityConfig;
import io.easyspring.security.social.properties.SocialProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 浏览器安全的配置类
 *
 * @author summer
 * @date 2019-01-21 14:40
 * @version V1.0.0-RELEASE
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    /**
     * 注入 security 的配置文件
     */
    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 注入 social 的基础参数配置
     * TODO 暂时引用, 使用 social 的相关配置, 后期需要去除
     */
    @Autowired
    private SocialProperties socialProperties;
    /**
     * 注入数据源
     */
    @Autowired
    private DataSource dataSource;
    /**
     * 注入 security 的用户 service
     * 因为此处的实现是在调用方进行实现的, 所以会报无实现的风险
     */
    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * 注入退出成功后的处理器
     */
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;


//    /**
//     * 注入表单验证的配置类
//     */
//    @Autowired
//    private FormAuthenticationConfig formAuthenticationConfig;

    /**
     * session 过期的处理策略
     */
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
    /**
     * session 失效的处理策略
     */
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;


    /**
     * 注入 social 登录的配置
     */
    @Autowired
    private SpringSocialConfigurer easySpringSocialConfigurer;

    /**
     * 注入验证码主类的配置类
     */
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    /**
     * 注入短信验证码的配置类
     */
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    /**
     * 重写父级的 security 配置, 使用自己的安全验证方案
     *
     * @author summer
     * @date 2019-01-21 14:41
     * @param http 安全请求对象
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 在 v5+ 中，该配置（表单登录）是默认配置
        // basic 登录（也就是弹框登录的）v5- 的版本默认

        // 表单登录的配置
        applyPasswordAuthenticationConfig(http);

        /*
         * 使用表单的方式登录
         * 最简单的修改默认配置的方法
         * 在 v5+ 中，该配置（表单登录）是默认配置
         */
		http
                // 在 UsernamePasswordAuthenticationFilter 过滤器之前加上验证码的过滤器
                .apply(validateCodeSecurityConfig)
                    .and()
                // 短信验证码的配置
                .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                // social 登录的配置
                .apply(easySpringSocialConfigurer)
                    .and()
                // 记住我的配置
                .rememberMe()
                    // 数据库操作实例
                    .tokenRepository(persistentTokenRepository())
                    // 设置有效时长
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    // 设置用户 service
                    .userDetailsService(userDetailsService)
                    .and()
                // 当浏览器 session 过期后, 控制 session 跳转
                .sessionManagement()
                    // session 失效的处理策略
                    .invalidSessionStrategy(invalidSessionStrategy)
                    // 配置最大的 session 数量, 如果配置成 1, 则只允许 1 个用户在线
                    .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                    // session 的登录策略, 当设置成 true 的时候, 则会阻止后来的登录行为
                    .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                    // session 过期的处理策略
                    .expiredSessionStrategy(sessionInformationExpiredStrategy)
                    .and()
                    .and()
                // 退出登录的配置
                .logout()
                    // 定义退出的请求地址
                    .logoutUrl(securityProperties.getBrowser().getSignOutUrl())
                    // 配置退出登录成功后的处理器
                    .logoutSuccessHandler(logoutSuccessHandler)
                    // 定义删除的 cookie
                    .deleteCookies(securityProperties.getBrowser().getSignOut().getDeleteCookies())
                    .and()
                // 权限校验规则
                .authorizeRequests()

                    // 请求拦截时, 忽略一下路径
                    .antMatchers(
                        // 未授权的时候的跳转的页面
                        SecurityConstants.SignIn.DEFAULT_AUTHENTICATION_URL,
                        // 手机验证码登录的接口
                        SecurityConstants.SignIn.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                        // 普通登录页面
                        securityProperties.getBrowser().getLoginPage(),
                        // 授权完成后若没有用户信息, 所要跳转的页面
                        // TODO 后期需要去除
                        socialProperties.getSignUpUrl(),
                        // TODO  后期需要抽离
                        "/user/regist",
                        // session 失效后跳转的地址
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                        // 退出登录成功后跳转的地址
                        securityProperties.getBrowser().getSignOut().getSignOutSuccessUrl(),
                        // 验证码接口,
                        SecurityConstants.Validate.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*")
                        .permitAll()
                    // 所有的权限校验
                    .anyRequest()
                    // 都需要权限校验
                    .authenticated()
                    .and()
                /*
                 * 忽略对伪造身份的拦截,
                 * v5+ 如果不忽略这个, 不会报错, 同时自定义的登录页面登录总是无效
                 * v5- 的时候, 如果不添加这个配置, 登录时会报错
                 */
                .csrf().disable();

    }

    /**
     * 设置记住我功能的数据源
     *
     * @author summer
     * @date 2019-01-21 20:45
     * @return org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
     * @version V1.0.0-RELEASE
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // 创建 jdbc 存储的实例
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        // 设置数据源
        tokenRepository.setDataSource(dataSource);
        // 自动创建数据库
		// tokenRepository.setCreateTableOnStartup(true);
		// 返回设置好的存储实例
        return tokenRepository;
    }

}
