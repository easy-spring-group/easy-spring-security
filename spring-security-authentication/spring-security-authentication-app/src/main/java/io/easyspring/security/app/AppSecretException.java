package io.easyspring.security.app;

/**
 * APP 认证的异常类
 *
 * @author summer
 * DateTime 2019-02-21 15:05
 * @version V1.0.0-RELEASE
 */
public class AppSecretException extends RuntimeException {

    /**
     * APP 密码异常类的构造方法
     *
     * Author summer
     * DateTime 2019-02-21 15:05
     * @param msg 异常信息
     * Version V1.0.0-RELEASE
     */
    public AppSecretException(String msg){
        super(msg);
    }

}
