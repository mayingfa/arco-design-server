package top.qiudb.module.user.mapper;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.qiudb.module.user.domain.entity.ResourceCategory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ResourceCategoryMapperTest {
    @Autowired
    ResourceCategoryMapper resourceCategoryMapper;

    @Test
    @Order(1)
    void initializeData() {
        List<ResourceCategory> resourceCategories = new ArrayList<>();
        resourceCategories.add(ResourceCategory.builder().name("商品管理").build());
        resourceCategories.add(ResourceCategory.builder().name("订单管理").build());
        resourceCategories.add(ResourceCategory.builder().name("人事管理").build());
        resourceCategories.add(ResourceCategory.builder().name("营销管理").build());
        Integer success = resourceCategoryMapper.insertBatchSomeColumn(resourceCategories);
        assertEquals(4, success);
    }

    @Test
    @Order(2)
    void queryAll() {
        List<ResourceCategory> resourceCategories = resourceCategoryMapper.selectList(null);
        resourceCategories.forEach(System.out::println);
        assertNotNull(resourceCategories);
        assertEquals(4, resourceCategories.size());
    }
}
