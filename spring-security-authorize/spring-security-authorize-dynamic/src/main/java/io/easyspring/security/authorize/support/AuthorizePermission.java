package io.easyspring.security.authorize.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.io.Serializable;

/**
 * 权限资源对象
 *
 * @author summer
 * DateTime 2019-03-06 13:49
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorizePermission implements Serializable {

    private static final long serialVersionUID = 2561175758974474102L;

    /**
     * 请求的地址
     */
    private String url;
    /**
     * 请求方式
     */
    private HttpMethod method;
}
