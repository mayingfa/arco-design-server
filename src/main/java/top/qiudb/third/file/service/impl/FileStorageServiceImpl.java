package top.qiudb.third.file.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.qiudb.common.constant.FileStorageEnum;
import top.qiudb.common.exception.Asserts;
import top.qiudb.common.properties.UploadProperties;
import top.qiudb.third.file.dto.UploadFile;
import top.qiudb.third.file.service.FileStorageService;
import top.qiudb.util.StringTools;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
@EnableConfigurationProperties(UploadProperties.class)
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private UploadProperties properties;

    private List<String> allowTypes;

    @PostConstruct
    private void init() {
        generateFolder(Paths.get(properties.getPath()));
        if (properties.getRestrictType()) {
            allowTypes = properties.getAllowTypes();
        }
    }

    @Override
    public UploadFile upload(MultipartFile multipartFile) {
        Path storePath = Paths.get(properties.getPath());
        return getUploadFile(storePath, multipartFile);
    }

    @Override
    public UploadFile upload(FileStorageEnum subPath, MultipartFile multipartFile) {
        Path storePath = Paths.get(properties.getPath(), subPath.getValue());
        generateFolder(storePath);
        return getUploadFile(storePath, multipartFile);
    }

    @Override
    public Resource load(String filename) {
        Path storePath = Paths.get(properties.getPath());
        return getResource(storePath, filename);
    }

    @Override
    public Resource load(FileStorageEnum subPath, String filename) {
        Path storePath = Paths.get(properties.getPath(), subPath.getValue());
        return getResource(storePath, filename);
    }

    /**
     * 创建文件目录
     *
     * @param storePath 存储路径
     */
    private void generateFolder(Path storePath) {
        try {
            if (!Files.exists(storePath)) {
                Files.createDirectory(storePath);
            }
        } catch (IOException e) {
            Asserts.fail("无法初始化上传的文件夹", e);
        }
    }

    /**
     * 上传文件
     *
     * @param storePath     存储路径
     * @param multipartFile 被上传的文件
     * @return 文件信息
     */
    private UploadFile getUploadFile(Path storePath, MultipartFile multipartFile) {
        if (allowTypes != null) {
            Asserts.checkTrue(allowTypes.contains(multipartFile.getContentType()), "文件上传类型错误");
        }
        String fileName = StringTools.generateRandomFilename(multipartFile.getOriginalFilename());
        Path filePath = storePath.resolve(fileName);
        try {
            Files.copy(multipartFile.getInputStream(), filePath);
        } catch (IOException e) {
            Asserts.fail("无法存储该文件", e);
        }
        return new UploadFile(fileName, filePath.toString().replaceAll("\\\\", "/"));
    }

    /**
     * 获取文件资源
     *
     * @param storePath 存储路径
     * @param fileName  文件名称
     * @return 文件资源
     */
    private Resource getResource(Path storePath, String fileName) {
        Path file = storePath.resolve(fileName);
        try {
            Resource resource = new UrlResource(file.toUri());
            Asserts.checkTrue(resource.exists() || resource.isReadable(), "不能读取文件");
            return resource;
        } catch (MalformedURLException e) {
            Asserts.fail("加载文件出错", e);
        }
        return null;
    }
}
