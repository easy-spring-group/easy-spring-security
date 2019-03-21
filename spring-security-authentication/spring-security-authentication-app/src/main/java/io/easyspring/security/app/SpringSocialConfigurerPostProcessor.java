package io.easyspring.security.app;

import io.easyspring.security.core.properties.SecurityConstants;
import io.easyspring.security.social.EasySpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * SpringSocial 的后处理器配置
 * 实现这个接口后 Spring 容器中所有的后处理器的 Bean 在初始化之前和初始化之后都会进入重写的方法
 *
 * @author summer
 * DateTime 2019-02-21 16:36
 * @version V1.0.0-RELEASE
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

    /**
     * 社交登录的配置类的 bean 的名称
     */
    private final String SPRING_SOCIAL_CONFIGURER_BEAN_NAME = "easySpringSocialConfigurer";

    /**
     * 在后处理器初始化之前
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(
     * java.lang.Object, java.lang.String)
     *
     * Author summer
     * DateTime 2019-02-21 16:37
     * @param bean 需要初始化的 Bean
     * @param beanName bean 的名称
     * @return java.lang.Object
     * Version V1.0.0-RELEASE
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 在后处理器初始化之后执行的方法
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(
     * java.lang.Object, java.lang.String)
     *
     * Author summer
     * DateTime 2019-02-21 16:50
     * @param bean 需要初始化的 Bean
     * @param beanName bean 的名称
     * @return java.lang.Object
     * Version V1.0.0-RELEASE
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 如果需要初始化的 bean 的名称是 社交登录的 配置类
        if(StringUtils.equals(beanName, SPRING_SOCIAL_CONFIGURER_BEAN_NAME)){
            // 执行 bean 的转换
            EasySpringSocialConfigurer config = (EasySpringSocialConfigurer)bean;
            // 设置新的注册地址
            // TODO 地址需要提取
            config.signupUrl(SecurityConstants.Social.SIGN_UP_URL);
            // 返回设置好的 配置
            return config;
        }

        // 其他的 bean 不做处理
        return bean;
    }

}
