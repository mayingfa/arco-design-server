package top.qiudb.third.message.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.qiudb.util.RegexpUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 发送手机验证码请求参数
 */
@Getter
@Setter
public class PhoneCodeParam {
    @NotEmpty(message = "手机号码不能为空")
    @Pattern(regexp = RegexpUtils.MOBILE_PHONE_REGEXP, message = "手机号码格式不正确")
    @ApiModelProperty(value = "手机号码",required = true)
    private String phone;
}
