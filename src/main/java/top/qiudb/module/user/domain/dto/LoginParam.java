package top.qiudb.module.user.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 用户名登录参数
 */
@Getter
@Setter
public class LoginParam {
    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名",required = true)
    private String userName;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}