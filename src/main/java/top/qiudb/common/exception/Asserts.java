package top.qiudb.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

/**
 * 断言处理类，用于抛出各种API异常
 */
@Slf4j
public class Asserts {
    private static final int UPDATE_FAILED_FLAG = 0;

    /**
     * 隐藏构造器
     */
    private Asserts() {
    }

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(String message, Throwable cause) {
        log.error(message, cause);
        throw new ApiException(message, cause);
    }

    public static void checkNull(Object data, String message) {
        if (null == data) {
            fail(message);
        }
    }

    public static void checkTrue(Boolean data, String message) {
        if (!data) {
            fail(message);
        }
    }

    public static void checkFalse(Boolean data, String message) {
        if (data) {
            fail(message);
        }
    }

    /**
     * 检查Mybatis更新状态
     *
     * @param updateNumber 更新标识
     * @param message      错误信息
     */
    public static void checkUpdate(Integer updateNumber, String message) {
        if (updateNumber.equals(UPDATE_FAILED_FLAG)) {
            fail(message);
        }
    }

    /**
     * 检查空集合
     *
     * @param list    集合列表
     * @param message 错误信息
     */
    public static void checkEmptyList(Collection<?> list, String message) {
        if (CollectionUtils.isEmpty(list)) {
            fail(message);
        }
    }
}
