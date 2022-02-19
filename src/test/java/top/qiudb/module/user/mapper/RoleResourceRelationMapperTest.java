package top.qiudb.module.user.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.qiudb.module.user.domain.entity.Resource;
import top.qiudb.module.user.domain.entity.Role;
import top.qiudb.module.user.domain.entity.RoleResourceRelation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleResourceRelationMapperTest {
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    RoleResourceRelationMapper roleResourceRelationMapper;

    @Test
    @Order(1)
    void initializeData() {
        List<RoleResourceRelation> rs = new ArrayList<>();
        Long roleId = getRoleId("商品管理员");
        Long productAddId = getResourceId("product-add");
        Long productUpdateId = getResourceId("product-update");
        Long productDeleteId = getResourceId("product-delete");
        rs.add(RoleResourceRelation.builder().roleId(roleId).resourceId(productAddId).build());
        rs.add(RoleResourceRelation.builder().roleId(roleId).resourceId(productUpdateId).build());
        rs.add(RoleResourceRelation.builder().roleId(roleId).resourceId(productDeleteId).build());
        Integer success = roleResourceRelationMapper.insertBatchSomeColumn(rs);
        assertEquals(3, success);
    }

    @Test
    @Order(2)
    void queryAll() {
        List<RoleResourceRelation> roleResourceRelations = roleResourceRelationMapper.selectList(null);
        roleResourceRelations.forEach(System.out::println);
        assertNotNull(roleResourceRelations);
        assertEquals(3, roleResourceRelations.size());
    }

    private Long getRoleId(String roleName) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getName, roleName);
        Role role = roleMapper.selectOne(queryWrapper);
        return Optional.ofNullable(role).map(Role::getId).orElse(1L);
    }

    private Long getResourceId(String resourceUrl) {
        LambdaQueryWrapper<Resource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Resource::getUrl, resourceUrl);
        Resource resource = resourceMapper.selectOne(queryWrapper);
        return Optional.ofNullable(resource).map(Resource::getId).orElse(1L);
    }
}
