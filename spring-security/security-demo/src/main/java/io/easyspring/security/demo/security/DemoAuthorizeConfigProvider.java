//package io.easyspring.security.demo.security;
//
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
//import org.springframework.stereotype.Component;
//
///**
// * @author : zhuqiang
// * @version : V1.0
// * @date : 2018/8/12 21:25
// */
//@Component
//@Order(Integer.MAX_VALUE)
//public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {
//
//    @Override
//    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
//        config
//                .antMatchers("/user/me").hasAnyAuthority("admin")
//        ;
//
//        // config.anyRequest().access("@rbacService.hasPermission(request,authentication)");
//
//        return true;
//    }
//}
