package top.qiudb.common.exception;

import lombok.Getter;

/**
 * 自定义API异常
 */
@Getter
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}
