package top.qiudb.third.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.qiudb.common.constant.GenderEnum;
import top.qiudb.module.user.domain.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * redis操作测试
 */
@SpringBootTest
class RedisServiceTest {
    @Autowired
    private RedisService redisService;

    @Test
    void simpleTest() {
        String key = "redis:user:1";
        User user = User.builder().id(1L).userName("123456").password("123456").age(20).phone("123456")
                .gender(GenderEnum.MAN).email("123456@qq.com").nickName("测试人员").build();
        redisService.set(key, user);
        User userCache = (User) redisService.get(key);
        assertEquals(user, userCache);
        assertTrue(redisService.del(key));
        assertFalse(redisService.hasKey(key));
    }

    @Test
    void hashTest() {
        String key = "redis:hash";
        HashMap<String, Object> value = new HashMap<>();
        value.put("name", "测试人员");
        value.put("age", 20);
        value.put("locked", false);
        redisService.hSetAll(key, value);
        Map<Object, Object> cacheMap = redisService.hGetAll(key);
        assertEquals(value, cacheMap);
        Object name = redisService.hGet(key, "name");
        assertEquals("测试人员", name);
        redisService.hDel(key, "age");
        assertFalse(redisService.hHasKey(key, "age"));
        assertTrue(redisService.del(key));
    }

    @Test
    void setTest() {
        String key = "redis:set";
        Set<String> value = new HashSet<>();
        value.add("用户1");
        value.add("用户2");
        value.add("用户3");
        Long len = redisService.sAdd(key, value.toArray());
        assertEquals(3, len);
        Set<Object> cacheSet = redisService.sMembers(key);
        assertEquals(value, cacheSet);
        assertTrue(redisService.sIsMember(key, "用户1"));
        assertEquals(1, redisService.sRemove(key, "用户1"));
        assertEquals(2, redisService.sSize(key));
        assertTrue(redisService.del(key));
    }

    @Test
    void listTest() {
        String key = "redis:list";
        List<String> value = new ArrayList<>();
        value.add("用户1");
        value.add("用户2");
        value.add("用户3");
        Long len = redisService.lPushAll(key, value.toArray());
        assertEquals(3, len);
        List<Object> cacheList = redisService.lRange(key, 0, -1);
        assertEquals(value, cacheList);
        assertEquals(4, redisService.lPush(key, "用户4"));
        assertEquals(0, redisService.lRemove(key, 0, "用户5"));
        assertEquals(4, redisService.lSize(key));
        assertTrue(redisService.del(key));
    }


}
