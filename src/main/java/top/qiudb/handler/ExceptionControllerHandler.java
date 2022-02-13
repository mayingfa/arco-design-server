package top.qiudb.handler;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.qiudb.common.constant.ResultCode;
import top.qiudb.common.domain.CommonResult;
import top.qiudb.common.exception.ApiException;


/**
 * 全局异常处理
 */
@RestControllerAdvice
public class ExceptionControllerHandler {
    @ExceptionHandler(ApiException.class)
    public CommonResult<String> apiExceptionHandler(ApiException e) {
        return new CommonResult<>(ResultCode.ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 从异常对象中拿到错误信息
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new CommonResult<>(ResultCode.VALIDATE_FAILED, errorMessage);
    }
}
