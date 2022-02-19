package top.qiudb.module.user.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.qiudb.module.user.domain.entity.Resource;
import top.qiudb.module.user.domain.entity.ResourceCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ResourceMapperTest {
    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    ResourceCategoryMapper resourceCategoryMapper;

    @Test
    @Order(1)
    void initializeData() {
        Long categoryId = getCategoryId("商品管理");
        List<Resource> resources = new ArrayList<>();
        Resource productAdd = Resource.builder().categoryId(categoryId).name("添加商品")
                .url("product-add").description("添加商品权限").build();
        Resource productUpdate = Resource.builder().categoryId(categoryId).name("修改商品")
                .url("product-update").description("添加商品权限").build();
        Resource productDelete = Resource.builder().categoryId(categoryId).name("删除商品")
                .url("product-delete").description("添加商品权限").build();
        resources.add(productAdd);
        resources.add(productUpdate);
        resources.add(productDelete);
        Integer success = resourceMapper.insertBatchSomeColumn(resources);
        assertEquals(3, success);
    }

    @Test
    @Order(2)
    void queryAll() {
        List<Resource> resources = resourceMapper.selectList(null);
        resources.forEach(System.out::println);
        assertNotNull(resources);
        assertEquals(3, resources.size());
    }

    private Long getCategoryId(String categoryName) {
        LambdaQueryWrapper<ResourceCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ResourceCategory::getName, categoryName);
        ResourceCategory resourceCategory = resourceCategoryMapper.selectOne(queryWrapper);
        return Optional.ofNullable(resourceCategory).map(ResourceCategory::getId).orElse(1L);
    }
}