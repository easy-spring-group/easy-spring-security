package com.bcdbook.security.core.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * request 请求的工具类
 *
 * @author summer
 * @date 2019-02-19 14:40
 * @version V1.0.0-RELEASE
 */
public class RequestUtils {

    /**
     * 认证信息的 key
     */
    private static final String AUTHORIZATION_KEY = "Authorization";
    /**
     * OAuth2 认证时, 客户信息的 前缀
     */
    private static final String OAUTH2_CLIENT_BASIC_PREFIX = "Basic ";
    /**
     * base64 的编码
     */
    private static final String DEFAULT_BASE64_CHARSET_NAME = "UTF-8";

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

    /**
     * OAuth2 认证时, 从请求头中解析 client 信息的解析方法
     *
     * @author summer
     * @date 2019-02-20 17:53
     * @param request 请求信息
     * @return java.lang.String[]
     * @version V1.0.0-RELEASE
     */
    public static String[] oAuth2ClientResolver(HttpServletRequest request) throws UnsupportedEncodingException {
        /*
         * 从请求头中获取 Authorization 信息
         */
        String header = request.getHeader(AUTHORIZATION_KEY);

        // 校验请求头中是否有 client 信息
        if (header == null || !header.startsWith(OAUTH2_CLIENT_BASIC_PREFIX)) {
            throw new UnapprovedClientAuthenticationException("请求头中无 client 信息");
        }

        /*
         * 解析 client 信息
         */
        // 获取 base64 编码的 client 信息
        byte[] base64Token = header.substring(6).getBytes(DEFAULT_BASE64_CHARSET_NAME);
        byte[] decoded;
        try {
            // 把 base64 的 client 信息解析成 byte 数组
            decoded = Base64.decodeBase64(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        // 解析成 clientId:clientSecret 结构的 client 数据
        String token = new String(decoded, DEFAULT_BASE64_CHARSET_NAME);
        // 获取分隔符下标
        int separatorIndex = token.indexOf(":");
        // 如果分隔符不存在, 则直接抛出异常
        if (separatorIndex == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        // 对 token 信息进行截取, 截取成 client 信息的数组
        return new String[] { token.substring(0, separatorIndex), token.substring(separatorIndex + 1) };
    }

}
