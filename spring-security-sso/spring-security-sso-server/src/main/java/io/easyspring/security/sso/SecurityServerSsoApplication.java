package io.easyspring.security.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SSO Server 启动类
 *
 * @author summer
 * DateTime 2019-02-23 09:49
 * @version V1.0.0-RELEASE
 */
@SpringBootApplication
public class SecurityServerSsoApplication {

    /**
     * 启动主入口
     *
     * @param args 传入的参数
     * Author summer
     * DateTime 2019-02-23 09:50
     * Version V1.0.0-RELEASE
     */
    public static void main(String[] args) {
        SpringApplication.run(SecurityServerSsoApplication.class, args);
    }
}
