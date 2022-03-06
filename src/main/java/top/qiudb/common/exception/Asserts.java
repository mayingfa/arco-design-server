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

    /**
     * 抛出自定义异常
     *
     * @param message 错误信息
     */
    public static void fail(String message) {
        throw new ApiException(message);
    }

    /**
     * 抛出自定义异常
     *
     * @param message 错误信息
     * @param cause   异常堆栈信息
     */
    public static void fail(String message, Throwable cause) {
        log.error(message, cause);
        throw new ApiException(message, cause);
    }

    /**
     * 校验参数是否为空，抛出校验异常
     *
     * @param data    被校验的数据
     * @param message 错误信息
     */
    public static void validatedNull(Object data, String message) {
        if (null == data) {
            throw new ValidatedException(message);
        }
    }

    /**
     * 检查参数是否为空，抛出API异常
     *
     * @param data    被检查的数据
     * @param message 错误信息
     */
    public static void checkNull(Object data, String message) {
        if (null == data) {
            fail(message);
        }
    }

    /**
     * 校验结果是否为真，若不是true则抛出自定义异常
     *
     * @param result  结果
     * @param message 错误信息
     */
    public static void checkTrue(Boolean result, String message) {
        if (!result) {
            fail(message);
        }
    }

    /**
     * 校验结果是否为假，若不是false则抛出自定义异常
     *
     * @param result  结果
     * @param message 错误信息
     */
    public static void checkFalse(Boolean result, String message) {
        if (result) {
            fail(message);
        }
    }

    /**
     * 检查Mybatis更新状态，若更新条数为0，则代表更新失败抛出异常
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
     * 检查空集合，集合列表为null或空元素，则抛出自定义异常
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
