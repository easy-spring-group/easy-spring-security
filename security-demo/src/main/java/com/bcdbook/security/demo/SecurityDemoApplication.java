package com.bcdbook.security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 主启动类
 *
 * @author summer
 * @date 2019-01-21 13:49
 * @version V1.0.0-RELEASE
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.bcdbook.security"
})
public class SecurityDemoApplication {

    /**
     * SpringBoot 项目的启动器
     *
     * @author summer
     * @date 2019-01-21 13:49
     * @param args 启动时的参数
     * @return void
     * @version V1.0.0-RELEASE
     */
    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }
}
