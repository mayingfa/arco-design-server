package top.qiudb.common.constant;

import lombok.Getter;

/**
 * 响应码枚举
 */
@Getter
public enum ResultCode {
    /**
     * SUCCESS 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * FAILED 接口响应失败
     */
    FAILED(400, "响应失败"),

    /**
     * VALIDATE_FAILED 参数校验失败
     */
    VALIDATE_FAILED(401, "参数校验失败"),

    /**
     * NO_PERMISSION 非法请求
     */
    NO_PERMISSION(402, "非法请求"),

    /**
     * METHOD_NOT_ALLOWED 不合法的请求方式
     */
    METHOD_NOT_ALLOWED(403, "不合法的请求方式"),

    /**
     * NOT_FOUND 找不到请求路径
     */
    NOT_FOUND(404, "找不到请求路径"),

    /**
     * BOUND_STATEMENT_NOT_FOUNT 找不到方法
     */
    BOUND_STATEMENT_NOT_FOUNT(405, "找不到方法"),

    /**
     * ERROR 操作失败
     */
    ERROR(500, "操作失败"),

    /**
     * SYSTEM_ERROR 系统异常
     */
    SYSTEM_ERROR(501, "系统异常"),

    /**
     * CONNECTION_ERROR 网络连接请求失败
     */
    CONNECTION_ERROR(502, "网络连接请求失败"),

    /**
     * DATABASE_ERROR 数据库异常
     */
    DATABASE_ERROR(503, "数据库异常"),

    /**
     * CACHE_ERROR 缓存异常
     */
    CACHE_ERROR(504, "缓存异常"),

    /**
     * LOGIN_ERROR 登录异常
     */
    LOGIN_ERROR(600, "登录异常"),

    /**
     * TOKEN_EXPIRED 身份过期
     */
    TOKEN_EXPIRED(601, "身份过期"),

    /**
     * ACCOUNT_BANNED 账号封禁
     */
    ACCOUNT_BANNED(602, "账号封禁");


    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
