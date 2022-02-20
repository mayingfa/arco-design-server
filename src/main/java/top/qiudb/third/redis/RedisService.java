package top.qiudb.third.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis操作Service
 */
public interface RedisService {

    /**
     * 保存属性
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间，单位：秒
     */
    void set(String key, Object value, long time);

    /**
     * 保存属性
     *
     * @param key   键
     * @param value 值
     */
    void set(String key, Object value);

    /**
     * 获取属性
     *
     * @param key 键
     * @return 值
     */
    Object get(String key);

    /**
     * 删除属性
     *
     * @param key 键
     * @return 是否删除成功
     */
    Boolean del(String key);

    /**
     * 批量删除属性
     *
     * @param keys 键列表
     * @return 删除数量
     */
    Long del(List<String> keys);

    /**
     * 设置过期时间
     *
     * @param key  键
     * @param time 过期时间，单位：秒
     * @return 是否设置成功
     */
    Boolean expire(String key, long time);

    /**
     * 获取过期时间
     *
     * @param key 键
     * @return 过期时间，单位：秒
     */
    Long getExpire(String key);

    /**
     * 判断是否有该属性
     *
     * @param key 键
     * @return 是否存在
     */
    Boolean hasKey(String key);

    /**
     * 按delta递增
     *
     * @param key   键
     * @param delta 递增值
     * @return 递增完后的值
     */
    Long incr(String key, long delta);

    /**
     * 按delta递减
     *
     * @param key   键
     * @param delta 递减值
     * @return 递减完后的值
     */
    Long decr(String key, long delta);

    /**
     * 获取Hash结构中的属性
     *
     * @param key     键
     * @param hashKey Hash键
     * @return Hash值
     */
    Object hGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     *
     * @param key     键
     * @param hashKey Hash键
     * @param value   Hash值
     * @param time    过期时间，单位：秒
     * @return 是否设置成功
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     *
     * @param key     键
     * @param hashKey Hash键
     * @param value   Hash值
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     *
     * @param key 键
     * @return Hash结构
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 直接设置整个Hash结构
     *
     * @param key  键
     * @param map  Hash结构
     * @param time 过期时间，单位：秒
     * @return 是否设置成功
     */
    Boolean hSetAll(String key, Map<String, Object> map, long time);

    /**
     * 直接设置整个Hash结构
     *
     * @param key 键
     * @param map Hash结构
     */
    void hSetAll(String key, Map<String, Object> map);

    /**
     * 删除Hash结构中的属性
     *
     * @param key     键
     * @param hashKey Hash键
     */
    void hDel(String key, Object... hashKey);

    /**
     * 判断Hash结构中是否有该属性
     *
     * @param key     键
     * @param hashKey Hash键
     * @return 是否存在
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * Hash结构中属性递增
     *
     * @param key     键
     * @param hashKey Hash键
     * @param delta   递增值
     * @return 递增后的值
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     *
     * @param key     键
     * @param hashKey Hash键
     * @param delta   递减值
     * @return 递减后的值
     */
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * 获取Set结构
     *
     * @param key 键
     * @return 值
     */
    Set<Object> sMembers(String key);

    /**
     * 向Set结构中添加属性
     *
     * @param key    键
     * @param values 值
     * @return 添加的数量
     */
    Long sAdd(String key, Object... values);

    /**
     * 向Set结构中添加属性
     *
     * @param key    键
     * @param time   过期时间，单位：秒
     * @param values 值
     * @return 添加数量
     */
    Long sAdd(String key, long time, Object... values);

    /**
     * 是否为Set中的属性
     *
     * @param key   键
     * @param value 值
     * @return 是否存在
     */
    Boolean sIsMember(String key, Object value);

    /**
     * 获取Set结构的长度
     *
     * @param key 键
     * @return 队列长度
     */
    Long sSize(String key);

    /**
     * 删除Set结构中的属性
     *
     * @param key    键
     * @param values 值
     * @return 被成功移除的元素的数量
     */
    Long sRemove(String key, Object... values);

    /**
     * 获取List结构中的属性
     *
     * @param key   键
     * @param start 起始位置， 0 表示列表的第一个元素
     * @param end   结束位置，-1 表示列表的最后一个元素
     * @return 范围内的值
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     *
     * @param key 键
     * @return 列表长度
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     *
     * @param key   键
     * @param index 索引下标
     * @return 对应的值
     */
    Object lIndex(String key, long index);

    /**
     * 向List结构中添加属性
     *
     * @param key   键
     * @param value 值
     * @return 列表的长度
     */
    Long lPush(String key, Object value);

    /**
     * 向List结构中添加属性
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间
     * @return 列表的长度
     */
    Long lPush(String key, Object value, long time);

    /**
     * 向List结构中批量添加属性
     *
     * @param key    键
     * @param values 值
     * @return 列表的长度
     */
    Long lPushAll(String key, Object... values);

    /**
     * 向List结构中批量添加属性
     *
     * @param key    键
     * @param time   过期时间，单位：秒
     * @param values 值
     * @return 列表的长度
     */
    Long lPushAll(String key, Long time, Object... values);

    /**
     * 从List结构中移除属性
     *
     * @param key   键
     * @param count 移除数量
     *              count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。
     *              count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。
     *              count = 0 : 移除表中所有与 VALUE 相等的值。
     * @param value 值
     * @return 被移除元素的数量。 列表不存在时返回 0
     */
    Long lRemove(String key, long count, Object value);
}
