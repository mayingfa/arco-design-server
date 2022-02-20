package top.qiudb.module.user.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import top.qiudb.common.annotation.Comment;
import top.qiudb.common.constant.GenderEnum;
import top.qiudb.common.constant.LockedEnum;
import top.qiudb.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Comment("管理员表")
@Table(name = "admin")
@EqualsAndHashCode(callSuper = true)
public class Admin extends BaseEntity implements Serializable {
    @Comment("用户名")
    @Column(name = "user_name", length = 64)
    private String userName;

    @Comment("密码")
    @Column(name = "password", length = 64)
    private String password;

    @Comment("昵称")
    @Column(name = "nick_name", length = 200)
    private String nickName;

    @Comment("年龄")
    @Column(name = "age", length = 3)
    private Integer age;

    @Comment("性别：0->女；1->男")
    @Column(name = "gender", length = 1)
    private GenderEnum gender;

    @Comment("邮箱")
    @Column(name = "email", length = 100)
    private String email;

    @Comment("手机号")
    @Column(name = "phone", length = 11)
    private String phone;

    @Comment("身份证号")
    @Column(name = "identity_card", length = 18)
    private String identityCard;

    @Comment("头像")
    @Column(name = "avatar", length = 500)
    private String avatar;

    @Comment("备注信息")
    @Column(name = "note", length = 500)
    private String note;

    @Comment("锁定状态：0->未锁定；1->已锁定")
    @Column(name = "locked", length = 1)
    @TableField(fill = FieldFill.INSERT)
    private LockedEnum locked;

    @Comment("最后登录时间")
    @Column(name = "login_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime loginTime;
}