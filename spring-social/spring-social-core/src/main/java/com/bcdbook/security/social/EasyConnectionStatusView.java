package com.bcdbook.security.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社交账号绑定状态的视图
 * 继承 spring 抽象的视图类
 *
 * @author summer
 * @date 2019-01-25 19:09
 * @annotation @Component("connect/status") 组件的名字要和 spring social 实现中视图的名字一致
 * @version V1.0.0-RELEASE
 */
@Component("connect/status")
public class EasyConnectionStatusView extends AbstractView {

    /**
     * 注入 json 的工具类
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 重写视图渲染的方法
     *
     * @author summer
     * @date 2019-01-25 19:12
     * @param model 模型数据
     * @param request 请求信息
     * @param response 返回信息
     * @return void
     * @version V1.0.0-RELEASE
     */
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        // 从模型数据中获取连接信息
        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");

        // 定义返回数据对象
        Map<String, Boolean> result = new HashMap<>();
        // 循环设置返回信息
        for (String key : connections.keySet()) {
            // key 是服务提供商的 id, 通过 key 获取服务提供商是否存在, 如果存在则为 true
            result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
        }

        // 输出封装好的信息
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

}
