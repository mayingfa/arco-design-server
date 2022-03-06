package top.qiudb;

import cn.dev33.satoken.secure.SaSecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import top.qiudb.common.constant.LockedEnum;
import top.qiudb.common.properties.HostAddressProperties;
import top.qiudb.common.properties.SuperAdminProperties;
import top.qiudb.module.user.domain.entity.Admin;
import top.qiudb.module.user.mapper.AdminMapper;
import top.qiudb.module.user.service.AdminService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 项目启动后初始化超级管理员账号
 */
@Slf4j
@Component
@EnableConfigurationProperties(SuperAdminProperties.class)
public class ArcoDesignServerRunner implements ApplicationRunner {
    private static final String DEFAULT_USERNAME = "超级管理员";
    private static final String DEFAULT_PASSWORD = "123456";

    @Resource
    SuperAdminProperties superAdmin;

    @Resource
    HostAddressProperties hostAddressProperties;

    @Resource
    AdminService adminService;

    @Resource
    AdminMapper adminMapper;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Executing command line runner...");
        if (StringUtils.isNotBlank(superAdmin.getEmail()) &&
                !adminService.exist(superAdmin.getEmail())) {
            Admin admin = new Admin();
            admin.setUserName(superAdmin.getEmail());
            admin.setEmail(superAdmin.getEmail());
            if (StringUtils.isBlank(superAdmin.getNickName())) {
                superAdmin.setNickName(DEFAULT_USERNAME);
            }
            admin.setNickName(superAdmin.getNickName());
            if (StringUtils.isBlank(superAdmin.getPassword())) {
                superAdmin.setPassword(DEFAULT_PASSWORD);
            }
            String encodePassword = SaSecureUtil.md5BySalt(superAdmin.getPassword(),
                    superAdmin.getEmail());
            admin.setPassword(encodePassword);
            admin.setLocked(LockedEnum.NOT_LOCKED);
            adminMapper.insert(admin);
            List<Long> roleIds = Arrays.asList(1L, 2L, 3L, 4L);
            adminService.updateRole(admin.getId(), roleIds);
        }
        log.info("Project started successfully.");
        printDocumentAddress();
    }

    void printDocumentAddress(){
        log.info("项目接口文档地址: {}doc.html", hostAddressProperties.getHostAddress());
    }
}