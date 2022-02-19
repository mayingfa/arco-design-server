package top.qiudb.module.user.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.qiudb.common.exception.Asserts;
import top.qiudb.module.user.domain.dto.AdminPageParam;
import top.qiudb.module.user.domain.dto.AdminParam;
import top.qiudb.module.user.domain.dto.LoginParam;
import top.qiudb.module.user.domain.dto.PhoneLoginParam;
import top.qiudb.module.user.domain.dto.UpdateAdminPasswordParam;
import top.qiudb.module.user.domain.entity.Admin;
import top.qiudb.module.user.domain.vo.AdminVo;
import top.qiudb.module.user.domain.vo.ResourceVo;
import top.qiudb.module.user.domain.vo.RoleVo;
import top.qiudb.module.user.mapper.AdminMapper;
import top.qiudb.module.user.service.AdminService;

import java.util.ArrayList;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;


    @Override
    public void register(AdminParam adminParam) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminParam, admin);
        if (getAdminCount(admin.getUserName()) > 0) {
            Asserts.fail("账号已被注册");
        }
        String encodePassword = SaSecureUtil.md5(admin.getPassword());
        admin.setPassword(encodePassword);
        Asserts.checkUpdate(adminMapper.insert(admin), "账号注册失败");
    }

    @Override
    public void login(LoginParam adminLoginParam) {
        AdminVo adminVo = getByUserName(adminLoginParam.getUserName());
        if (StringUtils.isNotBlank(adminVo.getPassword())) {
            String encodePassword = SaSecureUtil.md5(adminLoginParam.getPassword());
            if (encodePassword.equals(adminVo.getPassword())) {
                StpUtil.login(adminVo.getId());
            }
        }
        Asserts.fail("用户名或密码错误");
    }

    @Override
    public String phoneLogin(PhoneLoginParam phoneLoginParam) {
        return null;
    }

    @Override
    public AdminVo getById(Long adminId) {
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
        Admin admin = adminMapper.selectOne(queryWrapper);
        Asserts.checkNull(admin, "账号不存在");
        AdminVo adminVo = new AdminVo();
        BeanUtils.copyProperties(admin, adminVo);
        return adminVo;
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
        return null;
    }

    @Override
    public List<ResourceVo> getResourceList(Long adminId) {
        return null;
    }

    @Override
    public void update(AdminParam adminParam) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminParam, admin);
        Asserts.checkNull(admin.getId(), "管理员ID不允许为空");
        Admin originalAdmin = adminMapper.selectById(admin.getId());
        if (StringUtils.isNotBlank(admin.getPassword())) {
            String encodePassword = SaSecureUtil.md5(admin.getPassword());
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
    public void updatePassword(UpdateAdminPasswordParam updatePasswordParam) {

    }

    @Override
    public void updateRole(Long adminId, List<Long> roleIds) {

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

    private Integer getAdminCount(String userName) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUserName, userName);
        return adminMapper.selectCount(queryWrapper);
    }
}
