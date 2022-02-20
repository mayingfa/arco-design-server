package top.qiudb.module.user.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import top.qiudb.common.constant.LockedEnum;
import top.qiudb.common.constant.GenderEnum;
import top.qiudb.common.domain.BaseDto;
import top.qiudb.util.RegexpUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 创建和修改用户的请求参数
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserParam extends BaseDto implements Serializable {
    @NotNull(message = "用户名不能为空")
    @Size(min = 6, max = 11, message = "用户名长度必须是6-11个字符")
    private String userName;

    @NotNull(message = "用户密码不能为空")
    @Size(min = 6, max = 11, message = "密码长度必须是6-16个字符")
    private String password;

    @NotNull(message = "昵称不能为空")
    private String nickName;

    private Integer age;

    private GenderEnum gender;

    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = RegexpUtils.MOBILE_PHONE_REGEXP, message = "手机号格式不正确")
    private String phone;

    @Pattern(regexp = RegexpUtils.IDENTITY_CARD_REGEXP, message = "身份证格式不正确")
    private String identityCard;

    private String avatar;

    private String note;

    private LockedEnum locked;

    private LocalDateTime loginTime;
}