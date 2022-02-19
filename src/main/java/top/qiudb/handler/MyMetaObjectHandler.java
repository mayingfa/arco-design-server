package top.qiudb.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import top.qiudb.common.constant.DeleteEnum;
import top.qiudb.common.constant.LockedEnum;

import java.time.LocalDateTime;

/**
 * mybatis-plus 自动填充
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        Object status = getFieldValByName("status", metaObject);
        if (null == status) {
            setFieldValByName("status", DeleteEnum.NOT_DELETE, metaObject);
        }
        Object locked = getFieldValByName("locked", metaObject);
        if (null == locked) {
            setFieldValByName("locked", LockedEnum.NOT_LOCKED, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}