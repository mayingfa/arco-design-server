package top.qiudb.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import top.qiudb.common.constant.ResultCode;


/**
 * 自定义统一响应体
 */
@Getter
@Data
@ApiModel("统一响应体")
public class CommonResult<T> {
    @ApiModelProperty(value = "状态码", notes = "默认200成功")
    private Integer code;
    @ApiModelProperty(value = "响应信息", notes = "说明响应情况")
    private String message;
    @ApiModelProperty(value = "响应的具体数据")
    private T data;

    public CommonResult(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public CommonResult(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param message 提示信息
     * @param data    获取的数据
     */
    public static <T> CommonResult<T> success(String message, T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> error(T data) {
        return new CommonResult<>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage(), data);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     * @param data    获取的数据
     */
    public static <T> CommonResult<T> error(String message, T data) {
        return new CommonResult<>(ResultCode.ERROR.getCode(), message, data);
    }
}
