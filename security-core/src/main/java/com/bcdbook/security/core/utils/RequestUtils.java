package com.bcdbook.security.core.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * request 请求的工具类
 *
 * @author summer
 * @date 2019-02-19 14:40
 * @version V1.0.0-RELEASE
 */
public class RequestUtils {

    /**
     * 校验当前请求是否是 ajax 请求
     *
     * @author summer
     * @date 2019-01-07 17:34
     * @param request 请求对象
     * @return boolean
     * @version V1.0.0-RELEASE
     */
    public static boolean isHtml(HttpServletRequest request){
        // 传入参数校验
        if (request == null) {
            return false;
        }

        // 从请求头中获取 内容类型
        String accept = request.getHeader("Accept");
        // 如果请求内容不为空, 并且包含 json 则说明是 ajax 请求
        return !StringUtils.isEmpty(accept) && accept.contains("text/html");
    }
}
