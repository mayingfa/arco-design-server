package top.qiudb.module.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.qiudb.common.mapper.EasyBaseMapper;
import top.qiudb.module.user.domain.entity.Role;

@Mapper
public interface RoleMapper extends EasyBaseMapper<Role> {

}