package top.qiudb.module.user.mapper;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.qiudb.module.user.domain.entity.Role;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleMapperTest {
    @Autowired
    RoleMapper roleMapper;

    @Test
    @Order(1)
    void initializeData() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.builder().name("商品管理员").description("商品管理员").build());
        roles.add(Role.builder().name("订单管理员").description("订单管理员").build());
        roles.add(Role.builder().name("人事管理员").description("人事管理员").build());
        roles.add(Role.builder().name("营销管理员").description("营销管理员").build());
        Integer success = roleMapper.insertBatchSomeColumn(roles);
        assertEquals(4, success);
    }

    @Test
    @Order(2)
    void queryAll() {
        List<Role> roles = roleMapper.selectList(null);
        roles.forEach(System.out::println);
        assertNotNull(roles);
        assertEquals(4, roles.size());
    }
}
