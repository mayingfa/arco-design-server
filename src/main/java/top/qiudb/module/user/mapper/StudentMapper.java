package top.qiudb.module.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.qiudb.module.user.domain.entity.User;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<User> {
    /**
     * 查询全部学生
     * @return 学生列表
     */
    List<User> list();
}