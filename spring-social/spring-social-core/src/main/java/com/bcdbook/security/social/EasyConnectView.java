package com.bcdbook.security.social;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 社交账号绑定/解绑成功后返回的信息
 *
 * @author summer
 * @date 2019-01-28 15:38
 * @version V1.0.0-RELEASE
 */
public class EasyConnectView extends AbstractView {

    /**
     * 绑定成功后返回信息的 key
     */
    private static final String CONNECTION_NAME = "connection";

    /**
     * 重写视图渲染并返回的方法
     *
     * @author summer
     * @date 2019-01-28 15:39
     * @param model 用于封装视图的对象
     * @param request 请求信息
     * @param response 返回信息
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        // 设置返回对象的类型
        response.setContentType("text/html;charset=UTF-8");
        //
        if (model.get(CONNECTION_NAME) == null) {
            response.getWriter().write("<h3>解绑成功</h3>");
        } else {
            response.getWriter().write("<h3>绑定成功</h3>");
        }

    }

}
