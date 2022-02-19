package top.qiudb.module.user.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.qiudb.module.user.domain.entity.Admin;
import top.qiudb.module.user.domain.entity.AdminRoleRelation;
import top.qiudb.module.user.domain.entity.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminRoleRelationMapperTest {
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    AdminRoleRelationMapper adminRoleRelationMapper;

    @Test
    @Order(1)
    void initializeData() {
        List<AdminRoleRelation> rs = new ArrayList<>();
        Long adminId = getAdminId("管理员0");
        Long roleId = getRoleId("商品管理员");
        rs.add(AdminRoleRelation.builder().adminId(adminId).roleId(roleId).build());
        Integer success = adminRoleRelationMapper.insertBatchSomeColumn(rs);
        assertEquals(1, success);
    }

    @Test
    @Order(2)
    void queryAll() {
        List<AdminRoleRelation> adminRoleRelations = adminRoleRelationMapper.selectList(null);
        adminRoleRelations.forEach(System.out::println);
        assertNotNull(adminRoleRelations);
        assertEquals(1, adminRoleRelations.size());
    }

    private Long getAdminId(String adminName) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getNickName, adminName);
        Admin admin = adminMapper.selectOne(queryWrapper);
        return Optional.ofNullable(admin).map(Admin::getId).orElse(1L);
    }

    private Long getRoleId(String roleName) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getName, roleName);
        Role role = roleMapper.selectOne(queryWrapper);
        return Optional.ofNullable(role).map(Role::getId).orElse(1L);
    }
}
