package com.bcdbook.security.core.validate.code;

import com.bcdbook.security.core.properties.SecurityConstants;
import com.bcdbook.security.core.properties.SecurityProperties;
import com.bcdbook.security.core.properties.code.enums.ValidateCodeRepositoryTypeEnum;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码生成器的控制器
 *
 * @author summer
 * @date 2019-01-21 18:17
 * @version V1.0.0-RELEASE
 */
@RestController
public class ValidateCodeController {

    /**
     * 注入验证码处理器的管理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    /**
     * 注入 security 的配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor} 接口实现
     *
     * @author summer
     * @date 2019-01-17 13:38
     * @param request 请求对象
     * @param response 返回对象
     * @param type 验证码的类型(根据不同的验证码类型, 创建不同的验证码处理器)
     * @return void
     * @version V1.0.0-RELEASE
     */
    @GetMapping(SecurityConstants.Validate.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public String createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {

        /*
         * build ServletWebRequest 对象
         */
        // 封装 ServletWebRequest 对象
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
        // 获取新的 deviceId
        String deviceId = buildServletWebRequest(servletWebRequest);

        /*
         * 生成验证码
         */
        validateCodeProcessorHolder
                // 获取验证码的处理器
                .findValidateCodeProcessor(type)
                // 创建验证码
                .create(servletWebRequest);


        // 返回随机码给前台
        return deviceId;
    }

    /**
     * 对 servletWebRequest 进行处理,
     * 如果存储器是 Redis, 则生成 deviceId 并设置到 servletWebRequest
     *
     * @author summer
     * @date 2019-02-20 19:28
     * @param servletWebRequest 请求和返回信息
     * @return java.lang.String
     * @version V1.0.0-RELEASE
     */
    private String buildServletWebRequest(ServletWebRequest servletWebRequest) {
        // 定义 deviceId
        String deviceId = "";
        // 获取验证码存储器的类型
        ValidateCodeRepositoryTypeEnum repositoryType = securityProperties.getCode().getRepository();
        // 如果验证码的存储类型为 Redis
        if (ValidateCodeRepositoryTypeEnum.REDIS.equals(repositoryType)) {
            // 生成新的 deviceId
            deviceId = RandomStringUtils.randomNumeric(20);
            // 设置到 request 中
            servletWebRequest.setAttribute(SecurityConstants.Validate.DEFAULT_HEADER_DEVICE_ID_KEY,
                    deviceId,
                    SecurityConstants.Validate.DEFAULT_DEVICE_ID_EXPIRE);
        }

        // 返回生成的 deviceId
        return deviceId;
    }
}
