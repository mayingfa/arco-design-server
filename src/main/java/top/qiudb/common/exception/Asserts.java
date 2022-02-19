package top.qiudb.common.exception;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

/**
 * 断言处理类，用于抛出各种API异常
 */
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

    public static void checkNull(Object data, String message) {
        if (null == data) {
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
