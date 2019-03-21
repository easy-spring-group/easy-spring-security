package io.easyspring.security.sso.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 模拟客户端 的主启动类
 *
 * @author summer
 * DateTime 2019-02-23 20:03
 * @version V1.0.0-RELEASE
 */
@SpringBootApplication
@RestController
@EnableOAuth2Sso
public class SecurityClient1SsoApplication {

    /**
     * 获取当前在线用户
     *
     * Author summer
     * DateTime 2019-02-23 20:04
     * @param user 在线用户的认证信息
     * @return org.springframework.security.core.Authentication
     * Version V1.0.0-RELEASE
     */
    @GetMapping("/user")
    public Authentication user(Authentication user) {
        return user;
    }

    /**
     * 主启动类
     *
     * Author summer
     * DateTime 2019-02-23 20:20
     * @param args 启动参数
     * Version V1.0.0-RELEASE
     */
    public static void main(String[] args) {
        SpringApplication.run(SecurityClient1SsoApplication.class, args);
    }

}
