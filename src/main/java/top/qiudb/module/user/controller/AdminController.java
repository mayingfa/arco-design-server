package top.qiudb.module.user.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.qiudb.common.domain.CommonResult;
import top.qiudb.module.user.domain.dto.AdminPageParam;
import top.qiudb.module.user.domain.dto.AdminParam;
import top.qiudb.module.user.domain.dto.LoginParam;
import top.qiudb.module.user.domain.dto.PhoneLoginParam;
import top.qiudb.module.user.domain.dto.RegisterParam;
import top.qiudb.module.user.domain.dto.ResetPasswordParam;
import top.qiudb.module.user.domain.dto.UpdatePasswordParam;
import top.qiudb.module.user.domain.vo.AdminVo;
import top.qiudb.module.user.domain.vo.RoleVo;
import top.qiudb.module.user.service.AdminService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "后台用户管理")
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public CommonResult<String> register(@Validated @RequestBody RegisterParam param) {
        adminService.register(param);
        return CommonResult.success("注册成功");
    }

    @ApiOperation(value = "账号登录")
    @PostMapping(value = "/login/username")
    public CommonResult<SaTokenInfo> loginByUserName(@Validated @RequestBody LoginParam loginParam) {
        return CommonResult.success(adminService.login(loginParam));
    }

    @ApiOperation(value = "手机登录")
    @PostMapping(value = "/login/phone")
    public CommonResult<SaTokenInfo> loginByPhone(@Validated @RequestBody PhoneLoginParam loginParam) {
        return CommonResult.success(adminService.phoneLogin(loginParam));
    }

    @ApiOperation(value = "注销登录")
    @PostMapping(value = "/logout")
    public CommonResult<String> logout() {
        StpUtil.logout();
        return CommonResult.success("注销成功");
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping(value = "/info")
    public CommonResult<AdminVo> getAdminInfo() {
        long adminId = StpUtil.getLoginIdAsLong();
        AdminVo admin = adminService.getById(adminId);
        List<RoleVo> roles = adminService.getRoleList(adminId);
        if (!CollectionUtils.isEmpty(roles)) {
            List<String> roleNames = roles.stream().map(RoleVo::getName).collect(Collectors.toList());
            admin.setRole(roleNames);
        }
        return CommonResult.success(admin);
    }

    @ApiOperation("分页获取用户列表")
    @GetMapping(value = "/list")
    public CommonResult<IPage<AdminVo>> list(@RequestBody AdminPageParam pageParam) {
        return CommonResult.success(adminService.list(pageParam));
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping(value = "/info/{id}")
    public CommonResult<AdminVo> getItem(@PathVariable Long id) {
        return CommonResult.success(adminService.getById(id));
    }

    @ApiOperation("修改信息")
    @PostMapping(value = "/update")
    public CommonResult<String> update(@Validated @RequestBody AdminParam adminParam) {
        adminService.update(adminParam);
        return CommonResult.success("信息更新成功");
    }

    @ApiOperation("修改密码")
    @PostMapping(value = "/password/update")
    public CommonResult<String> updatePassword(@Validated @RequestBody UpdatePasswordParam passwordParam) {
        adminService.updatePassword(passwordParam);
        return CommonResult.success("密码修改成功");
    }

    @ApiOperation("重置密码")
    @PostMapping(value = "/password/reset")
    public CommonResult<String> resetPassword(@Validated @RequestBody ResetPasswordParam passwordParam) {
        adminService.resetPassword(passwordParam);
        return CommonResult.success("密码修改成功");
    }

    @ApiOperation("注销账号")
    @PostMapping(value = "/delete/{id}")
    public CommonResult<String> delete(@PathVariable Long id) {
        adminService.delete(id);
        return CommonResult.success("账号注销成功");
    }

    @ApiOperation("锁定账号")
    @PostMapping(value = "/locked/{id}")
    public CommonResult<String> locked(@PathVariable Long id) {
        adminService.locked(id);
        return CommonResult.success("账号锁定成功");
    }

    @ApiOperation("解锁账号")
    @PostMapping(value = "/unlock/{id}")
    public CommonResult<String> unlock(@PathVariable Long id) {
        adminService.unlock(id);
        return CommonResult.success("账号解锁成功");
    }

    @ApiOperation("用户分配角色")
    @PostMapping(value = "/role/update")
    public CommonResult<String> updateRole(@RequestParam("adminId") Long adminId,
                                           @RequestParam("roleIds") List<Long> roleIds) {
        adminService.updateRole(adminId, roleIds);
        return CommonResult.success("角色授权成功");
    }

    @ApiOperation(value = "上传用户头像")
    @PostMapping(value = "/upload/avatar")
    public CommonResult<Object> uploadAvatar(@RequestPart @RequestParam("file") MultipartFile file) {
        return CommonResult.success("头像上传成功",
                adminService.uploadAvatar(StpUtil.getLoginIdAsLong(), file));
    }
}
