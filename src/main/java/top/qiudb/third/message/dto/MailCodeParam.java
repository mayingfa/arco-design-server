package top.qiudb.third.message.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * 发送邮箱验证码请求参数
 */
@Getter
@Setter
public class MailCodeParam {
    @NotEmpty(message = "电子邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "电子邮箱",required = true)
    private String email;
}
