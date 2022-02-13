package top.qiudb.module.user.service;

import top.qiudb.module.user.domain.entity.User;

public interface UserService {
    /**
     * 添加用户
     * @param user 用户对象
     * @return 成功则返回"success"，失败则返回错误信息
     */
    String addUser(User user);

    /**
     * 查询用户
     * @return 成功则返回用户信息，失败则返回错误信息
     */
    User findUser();
}
