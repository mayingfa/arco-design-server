package top.qiudb.module.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.qiudb.common.constant.SexEnum;
import top.qiudb.module.user.domain.entity.User;
import top.qiudb.module.user.mapper.StudentMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
class MyBatisPlusTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    void testSelect() {
        List<User> students = studentMapper.selectList(null);
        students.forEach(System.out::println);
    }

    @Test
    void testSelectId() {
        List<User> ids = studentMapper.list();
        ids.forEach(System.out::println);
    }

    @Test
    void insert() {
        User student = User.builder().userName("小华").age(20).sex(SexEnum.MAN).build();
        int insertFlag = studentMapper.insert(student);
        log.info("插入影响行数,{} | 小华的ID: {}", insertFlag, student.getId());
    }

    @Test
    void update() {
        User student = User.builder().id(21L).userName("小华").age(30).build();
        int insertFlag = studentMapper.updateById(student);
        log.info("插入影响行数,{} | 小李的的年龄: {}", insertFlag, student.getAge());
    }

    @Test
    void updateWrapper() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name","小华");
        User student = User.builder().age(22).build();
        studentMapper.update(student, updateWrapper);
    }

    @Test
    void deleteById() {
        studentMapper.deleteById(23);
    }

    @Test
    void deleteWrapper() {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("name","小华");
        studentMapper.delete(wrapper);
    }

    @Test
    void deleteBatchIds() {
        List<Integer> idList = new ArrayList<>();
        idList.add(7);
        idList.add(8);
        studentMapper.deleteBatchIds(idList);
    }

    @Test
    void selectCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","小");
        System.out.println(studentMapper.selectCount(queryWrapper));
    }


    @Test
    void selectById() {
        User student = studentMapper.selectById(21);
        System.out.println(student);
    }

    @Test
    void selectBatchIds() {
        List<User> users = studentMapper.selectBatchIds(Arrays.asList(1, 3, 21));
        users.forEach(System.out::println);
    }

    @Test
    void selectOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","小华");
        User user = studentMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    void selectPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age",19);
        Page<User> page = new Page<>(1, 1);
        IPage<User> userIPage = studentMapper.selectPage(page, queryWrapper);
        System.out.println("数据总数:" + userIPage.getTotal());
        System.out.println("总页数:" + userIPage.getPages());
        System.out.println("当前页:" + userIPage.getCurrent());
        System.out.println("页大小:" + userIPage.getSize());
        userIPage.getRecords().forEach(System.out::println);
    }

    @Test
    void selectName() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name");
        List<User> students = studentMapper.selectList(queryWrapper);
        students.forEach(item-> System.out.println(item.getUserName()));
    }
}