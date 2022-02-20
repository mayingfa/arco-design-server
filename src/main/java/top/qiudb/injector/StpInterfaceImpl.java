package top.qiudb.injector;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.qiudb.module.user.domain.vo.ResourceVo;
import top.qiudb.module.user.service.AdminService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * sa-token 自定义权限验证
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Autowired
    AdminService adminService;

    /**
     * 权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        Long adminId = Long.valueOf(String.valueOf(loginId));
        List<ResourceVo> resourceList = adminService.getResourceList(adminId);
        return resourceList.stream().map(ResourceVo::getUrl).collect(Collectors.toList());
    }

    /**
     * 角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return Collections.emptyList();
    }

}