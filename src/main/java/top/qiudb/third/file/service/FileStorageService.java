package top.qiudb.third.file.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import top.qiudb.common.constant.FileStorageEnum;
import top.qiudb.third.file.dto.UploadFile;

/**
 * 文件存储服务
 **/
public interface FileStorageService {
    /**
     * 上传文件
     *
     * @param multipartFile 文件
     * @return 文件存储路径
     */
    UploadFile upload(MultipartFile multipartFile);

    /**
     * 上传文件到子目录
     *
     * @param subPath       子目录
     * @param multipartFile 文件
     * @return 文件存储路径
     */
    UploadFile upload(FileStorageEnum subPath, MultipartFile multipartFile);

    /**
     * 获取文件信息
     *
     * @param filename 文件名
     * @return 文件信息
     */
    Resource load(String filename);

    /**
     * 获取子目录文件信息
     *
     * @param subPath  子目录
     * @param filename 文件名
     * @return 文件信息
     */
    Resource load(FileStorageEnum subPath, String filename);
}
