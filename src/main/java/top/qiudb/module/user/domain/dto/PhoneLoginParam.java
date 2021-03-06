package top.qiudb.module.user.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.qiudb.util.RegexpUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 手机号登录参数
 */
@Getter
@Setter
public class PhoneLoginParam {
    @NotEmpty(message = "手机号码不能为空")
    @ApiModelProperty(value = "手机号码",required = true)
    @Pattern(regexp = RegexpUtils.MOBILE_PHONE_REGEXP, message = "手机号码格式不正确")
    private String phone;

    @NotEmpty(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码",required = true)
    private String authCode;
}