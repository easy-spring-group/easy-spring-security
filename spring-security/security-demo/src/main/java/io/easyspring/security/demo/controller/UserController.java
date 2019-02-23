package io.easyspring.security.demo.controller;

import io.easyspring.security.app.social.AppSignUpUtils;
import io.easyspring.security.core.properties.SecurityProperties;
import io.easyspring.security.demo.dto.User;
import io.easyspring.security.demo.dto.UserQueryCondition;
import com.fasterxml.jackson.annotation.JsonView;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户的前端控制器
 *
 * @author summer
 * @date 2019-01-21 14:00
 * @version V1.0.0-RELEASE
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    /**
     * 注入 social 登录的工具类
     */
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 注入 APP 登录的工具类
     */
    @Autowired
    private AppSignUpUtils appSignUpUtils;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 注册的示例页面
     *
     * @author summer
     * @date 2019-01-25 14:38
     * @param user 用户对象
     * @return void
     * @version V1.0.0-RELEASE
     */
    @PostMapping("/regist")
    public void regist(User user, HttpServletRequest request) {
        //注册用户
        //不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
        String userId = user.getUsername();

        /*
         * APP 环境下, 社交登录执行注册的方法
         */
        // 执行注册的后处理信息
        appSignUpUtils.doPostSignUp(new ServletWebRequest(request), userId);
    }

//    /**
//     * 注册的示例页面
//     *
//     * @author summer
//     * @date 2019-01-25 14:38
//     * @param user 用户对象
//     * @return void
//     * @version V1.0.0-RELEASE
//     */
//    @PostMapping("/regist")
//    public void regist(User user, HttpServletRequest request) {
//        //注册用户
//        //不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
//        String userId = user.getUsername();
//
//        /*
//         * 浏览器环境下的社交登录注册页面方法
//         */
//        // 这里的 userId 会设置成 UserConnection 的主键
//         providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
//    }

    /**
     * 根据查询条件查询用户集合的方法
     *
     * @author summer
     * @date 2019-01-21 13:52
     * @param condition 查询条件
     * @return java.util.List<io.easyspring.security.demo.dto.User>
     * @version V1.0.0-RELEASE
     */
    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public List<User> query(UserQueryCondition condition) {
        // compile group: 'org.apache.commons', name: 'commons-lang3', version: '3.7'
        // 一个反射工具类，这里把对象变成一个字符串，支持多种展示形式
        log.info(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

        // 伪造用户信息, 用于封装返回数据
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());

        return users;
    }

    /**
     * 获取用户详情
     *
     * @author summer
     * @date 2019-01-21 13:53
     * @param id 用户 id
     * @return io.easyspring.security.demo.dto.User
     * @version V1.0.0-RELEASE
     */
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable String id) {
        log.info("进入 getInfo 服务");

//        throw new UserNotExistException(id);
        User user = new User();
        user.setId(id);
        user.setUsername("summer");
        return user;
    }

    /**
     * 创建用户的方法
     *
     * @author summer
     * @date 2019-01-21 13:54
     * @param user 用户对象
     * @param errors 校验时如果出现异常, 用于封装异常信息的对象
     * @return io.easyspring.security.demo.dto.User
     * @version V1.0.0-RELEASE
     */
    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult errors) {
        // 循环获取异常信息的示例
        if (errors.hasErrors()) {
            // 循环输出异常信息
            errors.getAllErrors().forEach(err -> log.error(err.getDefaultMessage()));
        }

        // 输出想创建的用户对象
        log.info(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));

        user.setId("1");

        // 返回创建好的用户对象
        return user;
    }

    /**
     * 修改用户的方法
     *
     * @author summer
     * @date 2019-01-21 13:58
     * @param id 用户 id
     * @param user 想要修改的用户对象
     * @return io.easyspring.security.demo.dto.User
     * @version V1.0.0-RELEASE
     */
    @PutMapping("/{id:\\d+}")
    public User update(@PathVariable() String id, @RequestBody User user) {
        // 输出想要修改的用户信息
        log.info(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));

        user.setId(id);
        return user;
    }

    /**
     * 删除用户的方法
     *
     * @author summer
     * @date 2019-01-21 13:59
     * @param id 想要删除的用户的 id
     * @return void
     * @version V1.0.0-RELEASE
     */
    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        log.info("id: {}", id);
    }

    /**
     * 获取当前在线用户
     *
     * 注意: 当存储器是 jwt 的时候, security 中的 AuthenticationPrincipal 就不再是一个对象了, 而是一个经过 jwt 增强的字符串
     *
     * @author summer
     * @date 2019-01-21 18:04
     * @param user 注入用户对象
     * @return java.lang.Object
     * @version V1.0.0-RELEASE
     */
//    @GetMapping("/me")
//    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user, HttpServletRequest request) {
//        log.info(String.valueOf(request.getSession().getMaxInactiveInterval()));
//        return user;
//    }

    @GetMapping("/me")
    public Object getCurrentUser(Authentication user, HttpServletRequest request)
            throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException,
            IllegalArgumentException, UnsupportedEncodingException {

        String token = StringUtils.substringAfter(request.getHeader("Authorization"), "bearer ");

        // 从 token 中解析出 jwt 方式存储的增强信息
        Claims claims = Jwts.parser()
                .setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
                .parseClaimsJws(token)
                .getBody();

        String company = (String) claims.get("company");

        log.info("jwt token company: {}", company);

        return user;
    }

}
