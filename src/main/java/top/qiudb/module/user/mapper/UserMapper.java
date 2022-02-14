package top.qiudb.module.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.qiudb.common.mapper.EasyBaseMapper;
import top.qiudb.module.user.domain.entity.User;

@Mapper
public interface UserMapper extends EasyBaseMapper<User> {
    /**
     * 物理删除
     * @param userName 用户姓名
     * @return 执行条数
     */
    int deleteByName(@Param("userName") String userName);
}