package io.easyspring.security.browser.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 简单返回对象
 *
 * @author summer
 * @date 2019-01-21 16:51
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimpleResponse {

    /**
     * 返回的内容
     */
    private Object content;

}
