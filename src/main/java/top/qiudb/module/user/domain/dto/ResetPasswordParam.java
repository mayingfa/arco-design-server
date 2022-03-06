package top.qiudb.module.user.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 重置密码参数
 */
@Getter
@Setter
public class ResetPasswordParam {
    @NotEmpty(message = "邮箱地址不能为空")
    @ApiModelProperty(value = "邮箱地址", required = true)
    private String email;

    @NotEmpty(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true)
    private String authCode;

    @NotEmpty(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}