package top.qiudb.module.user.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;
import top.qiudb.module.user.domain.dto.AdminPageParam;
import top.qiudb.module.user.domain.dto.AdminParam;
import top.qiudb.module.user.domain.dto.LoginParam;
import top.qiudb.module.user.domain.dto.PhoneLoginParam;
import top.qiudb.module.user.domain.dto.RegisterParam;
import top.qiudb.module.user.domain.dto.UpdateAdminPasswordParam;
import top.qiudb.module.user.domain.vo.AdminVo;
import top.qiudb.module.user.domain.vo.ResourceVo;
import top.qiudb.module.user.domain.vo.RoleVo;

import java.util.List;

public interface AdminService {
    /**
     * 注册功能
     *
     * @param registerParam 注册账户参数
     */
    void register(RegisterParam registerParam);

    /**
     * 用户名登录
     * @param adminLoginParam 登录参数
     * @return 生成的token
     */
    SaTokenInfo login(LoginParam adminLoginParam);

    /**
     * 手机号登录
     *
     * @param phoneLoginParam 登录参数
     * @return 生成的token
     */
    SaTokenInfo phoneLogin(PhoneLoginParam phoneLoginParam);

    /**
     * 根据ID获取管理员信息
     *
     * @param adminId 唯一标识
     * @return Admin
     */
    AdminVo getById(Long adminId);

    /**
     * 根据用户名获取管理员信息
     *
     * @param userName 用户名
     * @return Admin
     */
    AdminVo getByUserName(String userName);

    /**
     * 根据手机号码获取管理员信息
     *
     * @param phone 手机号码
     * @return Admin
     */
    AdminVo getByPhone(String phone);

    /**
     * 分页查询管理员信息
     * @param adminPageParam 分页查询参数
     * @return 分页信息
     */
    IPage<AdminVo> list(AdminPageParam adminPageParam);

    /**
     * 获取管理员对应角色
     *
     * @param adminId 管理员唯一标识
     * @return 角色列表
     */
    List<RoleVo> getRoleList(Long adminId);

    /**
     * 获取指定管理员的可访问资源
     *
     * @param adminId 管理员唯一标识
     * @return 资源权限列表
     */
    List<ResourceVo> getResourceList(Long adminId);

    /**
     * 修改指定用户信息
     *
     * @param adminParam 管理员信息
     */
    void update(AdminParam adminParam);

    /**
     * 删除指定用户
     *
     * @param adminId 管理员唯一标识
     */
    void delete(Long adminId);

    /**
     * 锁定指定用户
     *
     * @param adminId 管理员唯一标识
     */
    void locked(Long adminId);

    /**
     * 解锁指定用户
     *
     * @param adminId 管理员唯一标识
     */
    void unlock(Long adminId);

    /**
     * 修改密码
     * @param updatePasswordParam 修改密码参数
     */
    void updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * 更新管理员角色关系
     * @param adminId 管理员唯一标识
     * @param roleIds 角色唯一标识列表
     */
    @Transactional(rollbackFor = Exception.class)
    void updateRole(Long adminId, List<Long> roleIds);
}
