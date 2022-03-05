package top.qiudb.module.user.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.qiudb.common.constant.LockedEnum;
import top.qiudb.common.exception.Asserts;
import top.qiudb.module.user.domain.dto.AdminPageParam;
import top.qiudb.module.user.domain.dto.AdminParam;
import top.qiudb.module.user.domain.dto.LoginParam;
import top.qiudb.module.user.domain.dto.PhoneLoginParam;
import top.qiudb.module.user.domain.dto.RegisterParam;
import top.qiudb.module.user.domain.dto.UpdateAdminPasswordParam;
import top.qiudb.module.user.domain.entity.Admin;
import top.qiudb.module.user.domain.entity.AdminRoleRelation;
import top.qiudb.module.user.domain.entity.Resource;
import top.qiudb.module.user.domain.entity.Role;
import top.qiudb.module.user.domain.vo.AdminVo;
import top.qiudb.module.user.domain.vo.ResourceVo;
import top.qiudb.module.user.domain.vo.RoleVo;
import top.qiudb.module.user.mapper.AdminMapper;
import top.qiudb.module.user.mapper.AdminRoleRelationMapper;
import top.qiudb.module.user.service.AdminService;
import top.qiudb.third.redis.RedisService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    AdminRoleRelationMapper adminRoleRelationMapper;

    @Value("${redis.key.prefix.authCode}")
    private String keyPrefix;

    @Value("${redis.key.expire.authCode}")
    private Long keyExpire;


    @Override
    public void register(RegisterParam registerParam) {
        String authCodeKey = keyPrefix + registerParam.getEmail();
        Asserts.checkTrue(redisService.hasKey(authCodeKey), "验证码已过期，请重新获取");
        String authCode = String.valueOf(redisService.get(authCodeKey));
        Asserts.checkTrue(registerParam.getAuthCode().equals(authCode), "验证码错误");
        if (getAdminCount(registerParam.getEmail()) > 0) {
            Asserts.fail("账号已被注册");
        }
        Admin admin = new Admin();
        admin.setNickName(registerParam.getNickName());
        admin.setUserName(registerParam.getEmail());
        admin.setEmail(registerParam.getEmail());
        String encodePassword = SaSecureUtil.md5BySalt(registerParam.getPassword(), admin.getUserName());
        admin.setPassword(encodePassword);
        Asserts.checkUpdate(adminMapper.insert(admin), "账号注册失败");
    }

    @Override
    public SaTokenInfo login(LoginParam param) {
        AdminVo adminVo = getByUserName(param.getUserName());
        String encodePassword = SaSecureUtil.md5BySalt(param.getPassword(), param.getUserName());
        if (!encodePassword.equals(adminVo.getPassword())) {
            Asserts.fail("用户名或密码错误");
        }
        if (LockedEnum.LOCKED.equals(adminVo.getLocked())) {
            StpUtil.disable(adminVo.getId(), -1);
        }
        StpUtil.login(adminVo.getId());
        return StpUtil.getTokenInfo();
    }

    @Override
    public SaTokenInfo phoneLogin(PhoneLoginParam phoneLoginParam) {
        String phone = phoneLoginParam.getPhone();
        String authCodeKey = keyPrefix + phone;
        Asserts.checkTrue(redisService.hasKey(authCodeKey), "验证码已过期，请重新获取");
        String authCode = String.valueOf(redisService.get(authCodeKey));
        Asserts.checkTrue(phoneLoginParam.getAuthCode().equals(authCode), "验证码错误");
        StpUtil.login(getByPhone(phone).getId());
        return StpUtil.getTokenInfo();
    }

    @Override
    public AdminVo getById(Long adminId) {
        Asserts.validatedNull(adminId, "ID不允许为空");
        Admin admin = adminMapper.selectById(adminId);
        Asserts.checkNull(admin, "账号不存在");
        AdminVo adminVo = new AdminVo();
        BeanUtils.copyProperties(admin, adminVo);
        return adminVo;
    }

    @Override
    public AdminVo getByUserName(String userName) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUserName, userName);
        return queryAdminInfo(queryWrapper);
    }

    @Override
    public AdminVo getByPhone(String phone) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getPhone, phone);
        return queryAdminInfo(queryWrapper);
    }

    @Override
    public IPage<AdminVo> list(AdminPageParam adminPageParam) {
        Page<Admin> page = new Page<>(adminPageParam.getPageNum(), adminPageParam.getPageSize());
        IPage<Admin> adminPage = adminMapper.selectPage(page, new LambdaQueryWrapper<>());
        Asserts.checkNull(adminPage.getRecords(), "查询失败");
        return getAdminVoPage(adminPage);
    }


    @Override
    public List<RoleVo> getRoleList(Long adminId) {
        Asserts.validatedNull(adminId, "ID不允许为空");
        List<Role> roles = adminRoleRelationMapper.getRoleList(adminId, LockedEnum.NOT_LOCKED);
        Asserts.checkNull(roles, "获取角色信息失败");
        List<RoleVo> roleVos = new ArrayList<>();
        for (Role role : roles) {
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(role, roleVo);
            roleVos.add(roleVo);
        }
        return roleVos;
    }

    @Override
    public List<ResourceVo> getResourceList(Long adminId) {
        Asserts.validatedNull(adminId, "ID不允许为空");
        List<Resource> resources = adminRoleRelationMapper.getResourceList(adminId);
        Asserts.checkNull(resources, "获取资源权限失败");
        List<ResourceVo> resourceVos = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceVo resourceVo = new ResourceVo();
            BeanUtils.copyProperties(resource, resourceVo);
            resourceVos.add(resourceVo);
        }
        return resourceVos;
    }

    @Override
    public void update(AdminParam adminParam) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminParam, admin);
        Asserts.validatedNull(admin.getId(), "管理员ID不允许为空");
        Admin originalAdmin = adminMapper.selectById(admin.getId());
        if (StringUtils.isNotBlank(admin.getPassword())) {
            String encodePassword = SaSecureUtil.md5BySalt(admin.getPassword(), admin.getUserName());
            //与原加密密码相同则不需要修改
            if (encodePassword.equals(originalAdmin.getPassword())) {
                admin.setPassword(null);
            } else {
                admin.setPassword(encodePassword);
            }
        }
        Asserts.checkUpdate(adminMapper.updateById(admin), "信息更新失败");
    }

    @Override
    public void delete(Long adminId) {
        Admin admin = adminMapper.selectById(adminId);
        if (null != admin) {
            Asserts.checkUpdate(adminMapper.deleteById(adminId), "账号注销失败");
        }
    }

    @Override
    public void locked(Long adminId) {
        Asserts.validatedNull(adminId, "ID不允许为空");
        Asserts.checkTrue(exist(adminId), "账号不存在");
        StpUtil.kickout(adminId);
        StpUtil.disable(adminId, -1);
        if (!StpUtil.isDisable(adminId)) {
            Asserts.fail("账号锁定失败");
        }
        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setLocked(LockedEnum.LOCKED);
        Asserts.checkUpdate(adminMapper.updateById(admin), "账号锁定失败");
    }

    @Override
    public void unlock(Long adminId) {
        Asserts.validatedNull(adminId, "ID不允许为空");
        Asserts.checkTrue(exist(adminId), "账号不存在");
        StpUtil.untieDisable(adminId);
        if (StpUtil.isDisable(adminId)) {
            Asserts.fail("账号解锁失败");
        }
        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setLocked(LockedEnum.NOT_LOCKED);
        Asserts.checkUpdate(adminMapper.updateById(admin), "账号解锁失败");
    }

    @Override
    public boolean exist(Long adminId) {
        Asserts.validatedNull(adminId, "ID不允许为空");
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getId, adminId);
        return adminMapper.exists(queryWrapper);
    }

    @Override
    public boolean exist(String userName) {
        Asserts.validatedNull(userName, "用户名不允许为空");
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUserName, userName);
        return adminMapper.exists(queryWrapper);
    }

    @Override
    public void updatePassword(UpdateAdminPasswordParam param) {
        String userName = param.getUserName();
        AdminVo adminVo = getByUserName(userName);
        String encodePassword = SaSecureUtil.md5BySalt(param.getOldPassword(), param.getUserName());
        if (!encodePassword.equals(adminVo.getPassword())) {
            Asserts.fail("身份验证失败，当前密码错误");
        }
        String newPassword = SaSecureUtil.md5BySalt(param.getNewPassword(), param.getUserName());
        Admin admin = Admin.builder().id(adminVo.getId()).password(newPassword).build();
        Asserts.checkUpdate(adminMapper.updateById(admin), "密码修改失败");
        StpUtil.logout(adminVo.getId());
    }

    @Override
    public void updateRole(Long adminId, List<Long> roleIds) {
        deleteOriginalRole(adminId);
        createNewRole(adminId, roleIds);
    }

    private IPage<AdminVo> getAdminVoPage(IPage<Admin> adminPage) {
        List<AdminVo> adminVos = new ArrayList<>();
        for (Admin admin : adminPage.getRecords()) {
            AdminVo adminVo = new AdminVo();
            BeanUtils.copyProperties(admin, adminVo);
            adminVos.add(adminVo);
        }
        return new Page<AdminVo>().setRecords(adminVos)
                .setPages(adminPage.getPages())
                .setTotal(adminPage.getTotal());
    }

    /**
     * 获取管理员数量
     *
     * @param userName 管理员用户名
     * @return 数量
     */
    private Long getAdminCount(String userName) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUserName, userName);
        return adminMapper.selectCount(queryWrapper);
    }

    /**
     * 删除原有角色
     *
     * @param adminId 管理员唯一标识
     */
    private void deleteOriginalRole(Long adminId) {
        Asserts.validatedNull(adminId, "ID不允许为空");
        LambdaQueryWrapper<AdminRoleRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminRoleRelation::getAdminId, adminId);
        Long count = adminRoleRelationMapper.selectCount(queryWrapper);
        LambdaUpdateWrapper<AdminRoleRelation> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AdminRoleRelation::getAdminId, adminId);
        int deleteNumber = adminRoleRelationMapper.delete(updateWrapper);
        if (count.intValue() != deleteNumber) {
            Asserts.fail("角色授权失败");
        }
    }

    /**
     * 创建新的角色关系
     *
     * @param adminId 管理员唯一标识
     * @param roleIds 角色唯一标识列表
     */
    private void createNewRole(Long adminId, List<Long> roleIds) {
        Asserts.checkEmptyList(roleIds, "角色列表不允许为空");
        ArrayList<AdminRoleRelation> adminRoleRelations = new ArrayList<>();
        for (Long roleId : roleIds) {
            AdminRoleRelation adminRoleRelation = new AdminRoleRelation();
            adminRoleRelation.setAdminId(adminId);
            adminRoleRelation.setRoleId(roleId);
            adminRoleRelations.add(adminRoleRelation);
        }
        Asserts.checkUpdate(adminRoleRelationMapper.insertBatchSomeColumn(adminRoleRelations),
                "角色授权失败");
    }

    /**
     * 根据QueryWrapper查询管理员信息
     *
     * @param queryWrapper 查询条件
     * @return 管理员信息
     */
    private AdminVo queryAdminInfo(LambdaQueryWrapper<Admin> queryWrapper) {
        Admin admin = adminMapper.selectOne(queryWrapper);
        Asserts.checkNull(admin, "账号不存在");
        AdminVo adminVo = new AdminVo();
        BeanUtils.copyProperties(admin, adminVo);
        return adminVo;
    }
}
