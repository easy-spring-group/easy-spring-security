package com.bcdbook.security.sso.client;

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
 * @date 2019-02-23 20:03
 * @version V1.0.0-RELEASE
 */
@SpringBootApplication
@RestController
@EnableOAuth2Sso
public class SecurityClient1SsoApplication {

    /**
     * TODO
     *
     * @author summer
     * @date 2019-02-23 20:04
     * @param user
     * @return org.springframework.security.core.Authentication
     * @version V1.0.0-RELEASE
     */
    @GetMapping("/user")
    public Authentication user(Authentication user) {
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityClient1SsoApplication.class, args);
    }

}
