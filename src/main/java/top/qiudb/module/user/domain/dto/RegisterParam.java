package top.qiudb.module.user.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 管理员注册请求参数
 */
@Getter
@Setter
public class RegisterParam {
    @NotEmpty(message = "昵称不能为空")
    @ApiModelProperty(value = "昵称",required = true)
    private String nickName;

    @NotNull(message = "电子邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "电子邮箱",required = true)
    private String email;

    @NotNull(message = "验证码不能为空")
    @ApiModelProperty(value = "邮箱验证码",required = true)
    private String authCode;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
