package top.qiudb.module.user.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 手机号登录参数
 */
@Getter
@Setter
public class PhoneLoginParam {
    @NotEmpty(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号码",required = true)
    private String phone;

    @NotEmpty(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码",required = true)
    private String authCode;
}