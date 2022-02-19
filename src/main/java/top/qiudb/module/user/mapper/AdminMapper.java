package top.qiudb.module.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.qiudb.common.mapper.EasyBaseMapper;
import top.qiudb.module.user.domain.entity.Admin;

@Mapper
public interface AdminMapper extends EasyBaseMapper<Admin> {

}