package top.qiudb.handler;

import cn.dev33.satoken.exception.DisableLoginException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
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
    public CommonResult<String> handlerApiException(ApiException e) {
        return new CommonResult<>(ResultCode.ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 从异常对象中拿到错误信息
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new CommonResult<>(ResultCode.VALIDATE_FAILED, errorMessage);
    }

    @ExceptionHandler(NotLoginException.class)
    public CommonResult<String> handlerNotLoginException(NotLoginException e) {
        String message;
        switch (e.getType()) {
            case NotLoginException.NOT_TOKEN:
                message = "未提供身份验证";
                break;
            case NotLoginException.INVALID_TOKEN:
                message = "身份验证无效";
                break;
            case NotLoginException.TOKEN_TIMEOUT:
                message = "身份验证已过期";
                break;
            case NotLoginException.BE_REPLACED:
                message = "账号已被顶下线";
                break;
            case NotLoginException.KICK_OUT:
                message = "账号已被踢下线";
                break;
            default:
                message = "当前会话未登录";
                break;
        }
        return new CommonResult<>(ResultCode.TOKEN_EXPIRED, message);
    }

    @ExceptionHandler(DisableLoginException.class)
    public CommonResult<String> handlerDisableLoginException() {
        return new CommonResult<>(ResultCode.ACCOUNT_BANNED, "此账号已被封禁");
    }

    @ExceptionHandler(NotPermissionException.class)
    public CommonResult<String> handlerNotPermissionException() {
        return new CommonResult<>(ResultCode.NOT_PERMISSIONS, "无此权限");
    }

}
