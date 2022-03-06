package top.qiudb.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 上传文件参数配置
 */
@Data
@ConfigurationProperties(prefix = "file.upload")
public class UploadProperties {
    /**
     * 存储路径
     */
    private String path;

    /**
     * 是否限制文件类型
     */
    private Boolean restrictType;

    /**
     * 文件上传允许的类型
     */
    private List<String> allowTypes;

}