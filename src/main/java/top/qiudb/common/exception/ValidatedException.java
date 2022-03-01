package top.qiudb.common.exception;

import lombok.Getter;

/**
 * 自定义参数校验异常
 */
@Getter
public class ValidatedException extends RuntimeException {
    public ValidatedException(String message) {
        super(message);
    }
}
