package top.qiudb.module.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.qiudb.common.constant.SexEnum;
import top.qiudb.module.user.domain.entity.User;
import top.qiudb.module.user.mapper.UserMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MyBatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    Long queryId() {
        return queryId("测试0");
    }

    Long queryId(String name) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, name);
        User user = userMapper.selectOne(queryWrapper);
        assertNotNull(user);
        return user.getId();
    }

    @Test
    @Order(1)
    void insert() {
        User user = User.builder().userName("1000").nickName("测试0").password("123456")
                .age(20).gender(SexEnum.WOMEN).email("123456@qq.com").phone("123456").build();
        int insertFlag = userMapper.insert(user);
        assertEquals(1, insertFlag);
    }

    @Test
    @Order(2)
    void batchInsert() {
        List<User> userList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            User user = User.builder().userName("100" + i).nickName("测试" + i).password("123456")
                    .age(15 * i).gender(SexEnum.MAN).email("123456@qq.com").phone("123456").build();
            userList.add(user);
        }
        int insertFlag = userMapper.insertBatchSomeColumn(userList);
        assertEquals(5, insertFlag);
    }

    @Test
    @Order(3)
    void selectAll() {
        List<User> userList = userMapper.selectList(null);
        assertFalse(userList.isEmpty());
        assertEquals(6, userList.size());
    }

    @Test
    @Order(4)
    void selectCount() {
        Long count = userMapper.selectCount(null);
        assertEquals(6, count);
    }

    @Test
    @Order(5)
    void selectCountBySex() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getGender, SexEnum.MAN);
        Long count = userMapper.selectCount(queryWrapper);
        assertEquals(5, count);
    }

    @Test
    @Order(6)
    void selectById() {
        User user = userMapper.selectById(queryId());
        assertNotNull(user);
        assertEquals("测试0", user.getUserName());
    }

    @Test
    @Order(7)
    void selectBatchIds() {
        Long userId = queryId();
        List<User> userList = userMapper.selectBatchIds(Arrays.asList(userId, userId + 1));
        assertFalse(userList.isEmpty());
        assertEquals(2, userList.size());
    }

    @Test
    @Order(8)
    void selectOne() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, "测试0");
        User user = userMapper.selectOne(queryWrapper);
        assertNotNull(user);
        assertEquals("1000", user.getUserName());
    }

    @Test
    @Order(9)
    void selectPage() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(User::getUserName, "测试");
        Page<User> page = new Page<>(1, 2);
        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);
        assertNotNull(userIPage);
        assertEquals(6, userIPage.getTotal());
        assertEquals(3, userIPage.getPages());
        assertEquals(1, userIPage.getCurrent());
        assertEquals(2, userIPage.getSize());
    }

    @Test
    @Order(10)
    void selectField() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(User::getUserName, User::getAge);
        List<User> userList = userMapper.selectList(queryWrapper);
        assertFalse(userList.isEmpty());
        assertEquals(6, userList.size());
    }

    @Test
    @Order(11)
    void updateById() {
        Long userId = queryId();
        User user = userMapper.selectById(userId);
        assertNotNull(user);
        assertEquals(SexEnum.WOMEN, user.getGender());
        user.setGender(SexEnum.MAN);
        int updateFlag = userMapper.updateById(user);
        user = userMapper.selectById(userId);
        assertEquals(1, updateFlag);
        assertEquals(SexEnum.MAN, user.getGender());
    }

    @Test
    @Order(12)
    void updateWrapper() {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getUserName, "测试0");
        User user = User.builder().age(30).build();
        int updateFlag = userMapper.update(user, updateWrapper);
        assertEquals(1, updateFlag);
    }

    @Test
    @Order(13)
    void deleteById() {
        int deleteFlag = userMapper.deleteById(queryId());
        assertEquals(1, deleteFlag);
    }

    @Test
    @Order(14)
    void deleteBatchIds() {
        Long userId = queryId("测试1");
        List<Long> idList = new ArrayList<>();
        idList.add(userId);
        idList.add(userId + 1);
        int deleteFlag = userMapper.deleteBatchIds(idList);
        assertEquals(2, deleteFlag);
    }

    @Test
    @Order(15)
    void deleteWrapper() {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.likeRight(User::getUserName, "测试");
        int deleteFlag = userMapper.delete(updateWrapper);
        assertNotEquals(0, deleteFlag);
    }

    @Test
    @Order(16)
    void physicallyDeleted() {
        int deleteFlag = userMapper.deleteByName("测试%");
        assertEquals(6, deleteFlag);
    }
}