package top.qiudb.module.user.service.impl;

import org.springframework.stereotype.Service;
import top.qiudb.common.constant.SexEnum;
import top.qiudb.common.exception.Asserts;
import top.qiudb.module.user.domain.entity.User;
import top.qiudb.module.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private static final String DEFAULT_NAME = "root";

    @Override
    public String addUser(User user) {
        // 直接编写业务逻辑
        if (DEFAULT_NAME.equals(user.getUserName())) {
            Asserts.fail("用户已存在");
        }
        return "添加成功";
    }

    @Override
    public User findUser() {
        User user = new User();
        user.setId(1L);
        user.setUserName(DEFAULT_NAME);
        user.setSex(SexEnum.MAN);
        return user;
    }
}
