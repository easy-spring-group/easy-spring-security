package io.easyspring.security.demo.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * 用户的 dto 对象
 *
 * @author summer
 * DateTime 2019-01-21 13:47
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
public class User {

    /**
     * 简单视图
     *
     * Author summer
     * DateTime 2019-03-07 00:11
     * Version V1.0.0-RELEASE
     */
    public interface UserSimpleView {
    }

    /**
     * 详细视图
     *
     * Author summer
     * DateTime 2019-03-07 00:11
     * Version V1.0.0-RELEASE
     */
    public interface UserDetailView extends UserSimpleView {
    }

    /**
     * id
     */
    private String id;
    /**
     * 用户名
     */
    @JsonView(UserSimpleView.class)
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @JsonView(UserDetailView.class)
    private String password;
    /**
     * 生日
     */
    @Past(message = "生日必须是过去时间")
    @JsonView(UserSimpleView.class)
    private Date birthday;
}
