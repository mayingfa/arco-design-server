package top.qiudb.module.user.mapper;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.qiudb.common.constant.GenderEnum;
import top.qiudb.module.user.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Order(1)
    void initializeData() {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User user = User.builder().userName("123456789" + i).password("123456")
                    .nickName("用户" + i).age(22).email("156245232" + i + "@qq.com")
                    .gender(GenderEnum.MAN).phone("1810568115" + i).identityCard("15114119880215141" + i)
                    .avatar("avatar.png").note("个人介绍").build();
            users.add(user);
        }
        Integer success = userMapper.insertBatchSomeColumn(users);
        assertEquals(3, success);
    }

    @Test
    @Order(2)
    void queryAll() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
        assertNotNull(users);
        assertEquals(3, users.size());
    }
}