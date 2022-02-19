package top.qiudb.module.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.qiudb.common.constant.LockedEnum;
import top.qiudb.common.mapper.EasyBaseMapper;
import top.qiudb.module.user.domain.entity.AdminRoleRelation;
import top.qiudb.module.user.domain.entity.Resource;
import top.qiudb.module.user.domain.entity.Role;

import java.util.List;

@Mapper
public interface AdminRoleRelationMapper extends EasyBaseMapper<AdminRoleRelation> {
    /**
     * 获取管理员对应角色
     * @param adminId 管理员唯一标识
     * @param locked  角色启用状态
     * @return 角色列表
     */
    List<Role> getRoleList(@Param("adminId") Long adminId, @Param("locked")LockedEnum locked);

    /**
     * 获取管理员所有可访问资源
     * @param adminId 管理员唯一标识
     * @return 资源列表
     */
    List<Resource> getResourceList(@Param("adminId") Long adminId);
}