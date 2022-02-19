package top.qiudb.module.user.mapper;

import cn.dev33.satoken.secure.SaSecureUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.qiudb.common.constant.SexEnum;
import top.qiudb.module.user.domain.entity.Admin;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminMapperTest {
    @Autowired
    AdminMapper adminMapper;

    @Test
    @Order(1)
    void initializeData() {
        List<Admin> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String encodePassword = SaSecureUtil.md5BySalt("123456", "123456789" + i);
            Admin admin = Admin.builder().userName("123456789" + i).password(encodePassword)
                    .nickName("管理员" + i).age(22).email("156245232" + i + "@qq.com")
                    .gender(SexEnum.WOMEN).phone("1810568115" + i).identityCard("15114119880215141" + i)
                    .avatar("avatar.png").note("个人介绍").build();
            users.add(admin);
        }
        Integer success = adminMapper.insertBatchSomeColumn(users);
        assertEquals(3, success);
    }

    @Test
    @Order(2)
    void queryAll() {
        List<Admin> admins = adminMapper.selectList(null);
        admins.forEach(System.out::println);
        assertNotNull(admins);
        assertEquals(3, admins.size());
    }
}
