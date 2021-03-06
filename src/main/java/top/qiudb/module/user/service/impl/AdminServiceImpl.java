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
import org.springframework.web.multipart.MultipartFile;
import top.qiudb.common.constant.FileStorageEnum;
import top.qiudb.common.constant.LockedEnum;
import top.qiudb.common.exception.Asserts;
import top.qiudb.common.properties.HostAddressProperties;
import top.qiudb.module.user.domain.dto.AdminPageParam;
import top.qiudb.module.user.domain.dto.AdminParam;
import top.qiudb.module.user.domain.dto.LoginParam;
import top.qiudb.module.user.domain.dto.PhoneLoginParam;
import top.qiudb.module.user.domain.dto.RegisterParam;
import top.qiudb.module.user.domain.dto.ResetPasswordParam;
import top.qiudb.module.user.domain.dto.UpdatePasswordParam;
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
import top.qiudb.third.file.dto.UploadFile;
import top.qiudb.third.file.service.FileStorageService;
import top.qiudb.third.redis.RedisService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    AdminRoleRelationMapper adminRoleRelationMapper;

    @Autowired
    HostAddressProperties hostAddress;

    @Value("${redis.key.prefix.authCode}")
    private String keyPrefix;

    @Value("${redis.key.expire.authCode}")
    private Long keyExpire;


    @Override
    public void register(RegisterParam registerParam) {
        if (exist(registerParam.getEmail())) {
            Asserts.fail("??????????????????");
        }
        String authCodeKey = keyPrefix + registerParam.getEmail();
        Asserts.checkTrue(redisService.hasKey(authCodeKey), "????????????????????????????????????");
        Asserts.checkTrue(redisService.checkAuthCode(authCodeKey,
                registerParam.getAuthCode()), "???????????????");
        Admin admin = new Admin();
        admin.setNickName(registerParam.getNickName());
        admin.setUserName(registerParam.getEmail());
        admin.setEmail(registerParam.getEmail());
        String encodePassword = SaSecureUtil.md5BySalt(registerParam.getPassword(), admin.getUserName());
        admin.setPassword(encodePassword);
        Asserts.checkUpdate(adminMapper.insert(admin), "??????????????????");
    }

    @Override
    public SaTokenInfo login(LoginParam param) {
        AdminVo adminVo = getByUserName(param.getUserName());
        String encodePassword = SaSecureUtil.md5BySalt(param.getPassword(), param.getUserName());
        if (!encodePassword.equals(adminVo.getPassword())) {
            Asserts.fail("????????????????????????");
        }
        if (LockedEnum.LOCKED.equals(adminVo.getLocked())) {
            StpUtil.disable(adminVo.getId(), -1);
        }
        StpUtil.login(adminVo.getId());
        return StpUtil.getTokenInfo();
    }

    @Override
    public SaTokenInfo phoneLogin(PhoneLoginParam param) {
        String phone = param.getPhone();
        AdminVo adminVo = getByPhone(phone);
        Asserts.checkNull(adminVo,"???????????????");
        String authCodeKey = keyPrefix + phone;
        Asserts.checkTrue(redisService.hasKey(authCodeKey), "????????????????????????????????????");
        Asserts.checkTrue(redisService.checkAuthCode(authCodeKey,
                param.getAuthCode()), "???????????????");
        StpUtil.login(adminVo.getId());
        return StpUtil.getTokenInfo();
    }

    @Override
    public AdminVo getById(Long adminId) {
        Asserts.validatedNull(adminId, "ID???????????????");
        Admin admin = adminMapper.selectById(adminId);
        Asserts.checkNull(admin, "???????????????");
        AdminVo adminVo = new AdminVo();
        BeanUtils.copyProperties(admin, adminVo);
        adminVo.setAvatar(hostAddress.getHostAddress()+adminVo.getAvatar());
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
        Asserts.checkNull(adminPage.getRecords(), "????????????");
        return getAdminVoPage(adminPage);
    }


    @Override
    public List<RoleVo> getRoleList(Long adminId) {
        Asserts.validatedNull(adminId, "ID???????????????");
        List<Role> roles = adminRoleRelationMapper.getRoleList(adminId, LockedEnum.NOT_LOCKED);
        Asserts.checkNull(roles, "????????????????????????");
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
        Asserts.validatedNull(adminId, "ID???????????????");
        List<Resource> resources = adminRoleRelationMapper.getResourceList(adminId);
        Asserts.checkNull(resources, "????????????????????????");
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
        Asserts.validatedNull(admin.getId(), "?????????ID???????????????");
        Admin originalAdmin = adminMapper.selectById(admin.getId());
        if (StringUtils.isNotBlank(admin.getPassword())) {
            String encodePassword = SaSecureUtil.md5BySalt(admin.getPassword(), admin.getUserName());
            //??????????????????????????????????????????
            if (encodePassword.equals(originalAdmin.getPassword())) {
                admin.setPassword(null);
            } else {
                admin.setPassword(encodePassword);
            }
        }
        Asserts.checkUpdate(adminMapper.updateById(admin), "??????????????????");
    }

    @Override
    public void delete(Long adminId) {
        Admin admin = adminMapper.selectById(adminId);
        if (null != admin) {
            Asserts.checkUpdate(adminMapper.deleteById(adminId), "??????????????????");
        }
    }

    @Override
    public void locked(Long adminId) {
        Asserts.validatedNull(adminId, "ID???????????????");
        Asserts.checkTrue(exist(adminId), "???????????????");
        StpUtil.kickout(adminId);
        StpUtil.disable(adminId, -1);
        if (!StpUtil.isDisable(adminId)) {
            Asserts.fail("??????????????????");
        }
        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setLocked(LockedEnum.LOCKED);
        Asserts.checkUpdate(adminMapper.updateById(admin), "??????????????????");
    }

    @Override
    public void unlock(Long adminId) {
        Asserts.validatedNull(adminId, "ID???????????????");
        Asserts.checkTrue(exist(adminId), "???????????????");
        StpUtil.untieDisable(adminId);
        if (StpUtil.isDisable(adminId)) {
            Asserts.fail("??????????????????");
        }
        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setLocked(LockedEnum.NOT_LOCKED);
        Asserts.checkUpdate(adminMapper.updateById(admin), "??????????????????");
    }

    @Override
    public boolean exist(Long adminId) {
        Asserts.validatedNull(adminId, "ID???????????????");
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getId, adminId);
        return adminMapper.exists(queryWrapper);
    }

    @Override
    public boolean exist(String userName) {
        Asserts.validatedNull(userName, "????????????????????????");
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUserName, userName);
        return adminMapper.exists(queryWrapper);
    }

    @Override
    public void updatePassword(UpdatePasswordParam param) {
        String userName = param.getUserName();
        AdminVo adminVo = getByUserName(userName);
        String encodePassword = SaSecureUtil.md5BySalt(param.getOldPassword(), param.getUserName());
        if (!encodePassword.equals(adminVo.getPassword())) {
            Asserts.fail("???????????????????????????????????????");
        }
        String newPassword = SaSecureUtil.md5BySalt(param.getNewPassword(), param.getUserName());
        Admin admin = Admin.builder().id(adminVo.getId()).password(newPassword).build();
        Asserts.checkUpdate(adminMapper.updateById(admin), "??????????????????");
        StpUtil.logout(adminVo.getId());
    }

    @Override
    public void resetPassword(ResetPasswordParam param) {
        String email = param.getEmail();
        AdminVo adminVo = getByUserName(email);
        Asserts.checkNull(adminVo,"???????????????");
        String authCodeKey = keyPrefix + email;
        Asserts.checkTrue(redisService.hasKey(authCodeKey), "????????????????????????????????????");
        Asserts.checkTrue(redisService.checkAuthCode(authCodeKey,
                param.getAuthCode()), "???????????????");
        redisService.del(authCodeKey);
        String password = SaSecureUtil.md5BySalt(param.getNewPassword(), adminVo.getUserName());
        Admin admin = Admin.builder().id(adminVo.getId()).password(password).build();
        Asserts.checkUpdate(adminMapper.updateById(admin), "??????????????????");
    }

    @Override
    public void updateRole(Long adminId, List<Long> roleIds) {
        deleteOriginalRole(adminId);
        createNewRole(adminId, roleIds);
    }

    @Override
    public String uploadAvatar(Long adminId, MultipartFile file) {
        UploadFile upload = fileStorageService.upload(FileStorageEnum.AVATAR, file);
        Asserts.checkTrue(StringUtils.isNotBlank(upload.getUrl()),"??????????????????");
        AdminParam adminParam = new AdminParam();
        adminParam.setId(adminId);
        adminParam.setAvatar(upload.getUrl());
        update(adminParam);
        return upload.getUrl();
    }

    private IPage<AdminVo> getAdminVoPage(IPage<Admin> adminPage) {
        List<AdminVo> adminVos = new ArrayList<>();
        for (Admin admin : adminPage.getRecords()) {
            AdminVo adminVo = new AdminVo();
            BeanUtils.copyProperties(admin, adminVo);
            adminVo.setAvatar(hostAddress.getHostAddress()+adminVo.getAvatar());
            adminVos.add(adminVo);
        }
        return new Page<AdminVo>().setRecords(adminVos)
                .setPages(adminPage.getPages())
                .setTotal(adminPage.getTotal());
    }

    /**
     * ??????????????????
     *
     * @param adminId ?????????????????????
     */
    private void deleteOriginalRole(Long adminId) {
        Asserts.validatedNull(adminId, "ID???????????????");
        LambdaQueryWrapper<AdminRoleRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminRoleRelation::getAdminId, adminId);
        Long count = adminRoleRelationMapper.selectCount(queryWrapper);
        LambdaUpdateWrapper<AdminRoleRelation> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AdminRoleRelation::getAdminId, adminId);
        int deleteNumber = adminRoleRelationMapper.delete(updateWrapper);
        if (count.intValue() != deleteNumber) {
            Asserts.fail("??????????????????");
        }
    }

    /**
     * ????????????????????????
     *
     * @param adminId ?????????????????????
     * @param roleIds ????????????????????????
     */
    private void createNewRole(Long adminId, List<Long> roleIds) {
        Asserts.checkEmptyList(roleIds, "???????????????????????????");
        ArrayList<AdminRoleRelation> adminRoleRelations = new ArrayList<>();
        for (Long roleId : roleIds) {
            AdminRoleRelation adminRoleRelation = new AdminRoleRelation();
            adminRoleRelation.setAdminId(adminId);
            adminRoleRelation.setRoleId(roleId);
            adminRoleRelations.add(adminRoleRelation);
        }
        Asserts.checkUpdate(adminRoleRelationMapper.insertBatchSomeColumn(adminRoleRelations),
                "??????????????????");
    }

    /**
     * ??????QueryWrapper?????????????????????
     *
     * @param queryWrapper ????????????
     * @return ???????????????
     */
    private AdminVo queryAdminInfo(LambdaQueryWrapper<Admin> queryWrapper) {
        Admin admin = adminMapper.selectOne(queryWrapper);
        Asserts.checkNull(admin, "???????????????");
        AdminVo adminVo = new AdminVo();
        BeanUtils.copyProperties(admin, adminVo);
        adminVo.setAvatar(hostAddress.getHostAddress()+adminVo.getAvatar());
        return adminVo;
    }
}
