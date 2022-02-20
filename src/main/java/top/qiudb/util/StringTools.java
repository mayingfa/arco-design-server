package top.qiudb.util;

import java.util.Date;
import java.util.Random;

/**
 * 字符串工具类
 */
public class StringTools {
    private static final int AUTH_CODE_DIGIT = 6;

    private StringTools() {
    }

    /**
     * 生成6位随机验证码
     *
     * @return 验证码
     */
    public static String getRandCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random((new Date()).getTime());
        for (int i = 0; i < AUTH_CODE_DIGIT; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
