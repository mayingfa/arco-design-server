package top.qiudb.module.user.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import top.qiudb.common.constant.GenderEnum;
import top.qiudb.common.constant.LockedEnum;
import top.qiudb.common.domain.BaseVo;

import java.time.LocalDateTime;

/**
 * 管理员信息
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AdminVo extends BaseVo {
    private String userName;

    private String password;

    private String nickName;

    private Integer age;

    private GenderEnum gender;

    private String email;

    private String phone;

    private String identityCard;

    private String avatar;

    private String note;

    private LockedEnum locked;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime loginTime;
}
