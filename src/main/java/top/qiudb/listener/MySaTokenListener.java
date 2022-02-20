package top.qiudb.listener;

import cn.dev33.satoken.listener.SaTokenListenerDefaultImpl;
import cn.dev33.satoken.stp.SaLoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.qiudb.module.user.domain.entity.Admin;
import top.qiudb.module.user.mapper.AdminMapper;

import java.time.LocalDateTime;

/**
 * 自定义侦听器的实现 
 */
@Component
public class MySaTokenListener extends SaTokenListenerDefaultImpl {
    @Autowired
    AdminMapper adminMapper;

    /** 每次登录时触发 */
    @Override
    public void doLogin(String loginType, Object loginId, SaLoginModel loginModel) {
        Long adminId = Long.valueOf(String.valueOf(loginId));
        Admin admin = Admin.builder().id(adminId).loginTime(LocalDateTime.now()).build();
        adminMapper.updateById(admin);
    }
}
