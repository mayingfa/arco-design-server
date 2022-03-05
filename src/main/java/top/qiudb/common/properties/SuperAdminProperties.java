package top.qiudb.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 超级管理员配置参数
 */
@Data
@ConfigurationProperties(prefix = "admin")
public class SuperAdminProperties {
    /**
     * 管理员邮箱
     */
    private String email;
    private String nickName;
    private String password;
}
