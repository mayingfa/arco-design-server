package top.qiudb.common.exception;

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

    public static void checkNull(Object data,String message) {
        if(null == data){
            fail(message);
        }
    }

    public static void checkUpdate(Integer updateNumber, String message) {
        if (updateNumber.equals(UPDATE_FAILED_FLAG)) {
            fail(message);
        }
    }
}
