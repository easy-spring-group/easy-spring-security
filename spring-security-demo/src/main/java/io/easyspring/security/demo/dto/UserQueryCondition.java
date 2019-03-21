package io.easyspring.security.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询条件对象
 * @author : zhuqiang
 * @version : V1.0
 * DateTime : 2018/8/1 23:19
 */
@NoArgsConstructor
@Data
public class UserQueryCondition {
    private String username;
    private int age;
    private int ageTo;
    private String xxx;
}
