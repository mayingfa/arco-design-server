package top.qiudb.third.file.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.qiudb.common.constant.FileStorageEnum;
import top.qiudb.common.domain.CommonResult;
import top.qiudb.third.file.service.FileStorageService;

@RestController
@Api(tags = "文件存储服务")
@RequestMapping("/third/file-storage/")
public class FileStorageController {
    @Autowired
    FileStorageService fileStorageService;

    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/upload")
    public CommonResult<Object> upload(@RequestPart @RequestParam("file") MultipartFile file) {
        return CommonResult.success(fileStorageService.upload(file));
    }

    @ApiOperation(value = "下载文件")
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Object> download(@PathVariable(name = "fileName") String fileName) {
        Resource resource = fileStorageService.load(fileName);
        return getObjectResponseEntity(fileName, resource);
    }

    @ApiOperation(value = "上传头像")
    @PostMapping(value = "/upload/avatar")
    public CommonResult<Object> uploadAvatar(@RequestPart @RequestParam("file") MultipartFile file) {
        return CommonResult.success(fileStorageService.upload(FileStorageEnum.AVATAR, file));
    }

    @ApiOperation(value = "下载头像")
    @GetMapping("/download/avatar/{fileName}")
    public ResponseEntity<Object> downloadAvatar(@PathVariable(name = "fileName") String fileName) {
        Resource resource = fileStorageService.load(FileStorageEnum.AVATAR, fileName);
        return getObjectResponseEntity(fileName, resource);
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @param resource 文件资源
     * @return 响应数据
     */
    private ResponseEntity<Object> getObjectResponseEntity(String fileName, Resource resource) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment;filename=\"%s", fileName));
        headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers)
                .contentType(MediaType.parseMediaType("application/txt"))
                .body(resource);
    }
}
