package io.easyspring.security.demo.code;

import io.easyspring.security.core.validate.code.ValidateCodeGenerator;
import io.easyspring.security.core.validate.code.image.ImageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 自定义的图片生成器, 用于覆盖原有的图片生成器
 *
 * @author summer
 * @date 2019-01-21 19:31
 * @version V1.0.0-RELEASE
 */
// @Component("imageCodeGenerator")
@Slf4j
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

    /**
     * 实现图片生成的方法
     *
     * @author summer
     * @date 2019-01-21 19:31
     * @param request Servlet 请求对象
     * @return io.easyspring.security.code.image.ImageCode
     * @version V1.0.0-RELEASE
     */
    @Override
    public ImageCode generate(ServletWebRequest request) {
        log.info("更高级的图形验证码生成代码");
        return null;
    }

}
