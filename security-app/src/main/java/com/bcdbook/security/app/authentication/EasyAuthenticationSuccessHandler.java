package com.bcdbook.security.app.authentication;

import com.bcdbook.security.core.utils.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * APP 环境下登录成功的处理器
 *
 * @author summer
 * @date 2019-02-19 17:23
 * @version V1.0.0-RELEASE
 */
@Component("easyAuthenticationSuccessHandler")
@Slf4j
public class EasyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * 注入 jackson 的 mapper 工具
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 注入客户端的 service
     */
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    public static void main(String[] args) {
        String ab = "easy-spring:easy-spring-secret";
        byte[] encodeBase64 = Base64.encodeBase64(ab.getBytes());
        System.out.println(new String(encodeBase64));
    }
    /**
     * 登录成功后的处理逻辑
     *
     * @author summer
     * @date 2019-02-19 17:23
     * @param request 请求信息
     * @param response 返回信息
     * @param authentication 登录的用户信息
     * @return void
     * @version V1.0.0-RELEASE
     */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

		log.info("登录成功");

		// 获取 client 的相关信息(clientId 和 clientSecret)
        String[] tokens = RequestUtils.oAuth2ClientResolver(request);

        // 生成 OAuth2Request
        OAuth2Request oAuth2Request = oAuth2RequestGenerator(tokens);

        // 创建 OAuth2Authentication
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        // 创建 OAuth2AccessToken
        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        // 把封装好的 token 信息输出到前端
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(token));

    }

    /**
     * OAuth2Request 的生成器
     *
     * @author summer
     * @date 2019-02-20 18:00
     * @param tokens client 信息
     * @return org.springframework.security.oauth2.provider.OAuth2Request
     * @version V1.0.0-RELEASE
     */
    private OAuth2Request oAuth2RequestGenerator(String[] tokens) {
        // 对 client 信息进行校验
        assert tokens.length == 2;

        /*
         * 封装 ClientDetails 信息
         */
        // 获取 clientId
        String clientId = tokens[0];
        // 获取 clientSecret
        String clientSecret = tokens[1];
        // 从 client 库中查询出 client 信息
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        // 对查询出的 clientDetails 信息做校验
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId 对应的配置信息不存在:" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret 不匹配:" + clientId);
        }

        /*
         * 创建 tokenRequest 对象
         */
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP,
                clientId,
                clientDetails.getScope(),
                "custom");

        // 创建 OAuth2Request 对象并返回
        return tokenRequest.createOAuth2Request(clientDetails);
    }

}
