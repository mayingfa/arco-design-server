package top.qiudb.third.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {
    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String url;
}
