package top.qiudb.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 接口文档参数配置
 */
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    /**
     * 是否开启接口文档
     */
    private boolean enable;

    /**
     * 维护人姓名
     */
    private String name;

    /**
     * 维护人url地址
     */
    private String url;

    /**
     * 维护人邮箱
     */
    private String email;

}
