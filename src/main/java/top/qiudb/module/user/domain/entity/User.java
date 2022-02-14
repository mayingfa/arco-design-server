package top.qiudb.module.user.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import top.qiudb.common.annotation.Comment;
import top.qiudb.common.constant.SexEnum;
import top.qiudb.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "user")
@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements Serializable {
    @Comment("用户账号")
    @Column(name = "account", length = 32)
    @NotNull(message = "用户账号不能为空")
    @Size(min = 6, max = 11, message = "账号长度必须是6-11个字符")
    private String account;

    @Comment("用户姓名")
    @Column(name = "user_name", length = 50)
    @NotNull(message = "用户姓名不能为空")
    private String userName;

    @Comment("用户密码")
    @Column(name = "password", length = 50)
    @NotNull(message = "用户密码不能为空")
    @Size(min = 6, max = 11, message = "密码长度必须是6-16个字符")
    private String password;

    @Comment("用户年龄")
    @Column(name = "age", length = 3)
    @NotNull(message = "用户年龄不能为空")
    private Integer age;

    @Comment("用户性别")
    @Column(name = "sex", length = 1)
    @NotNull(message = "用户性别不能为空")
    private SexEnum sex;

    @Comment("用户邮箱")
    @Column(name = "email", length = 20)
    @NotNull(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Comment("用户手机号")
    @Column(name = "phone", length = 20)
    @NotNull(message = "用户手机不能为空")
    private String phone;
}