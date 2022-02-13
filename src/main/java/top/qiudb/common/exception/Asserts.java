package top.qiudb.common.exception;

/**
 * 断言处理类，用于抛出各种API异常
 */
public class Asserts {
    /**
     * 隐藏构造器
     */
    private Asserts() {
    }

    public static void fail(String message) {
        throw new ApiException(message);
    }
}
